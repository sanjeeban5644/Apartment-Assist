const API_BASE_URL = 'http://localhost:8080';

// Check authentication
const userSession = JSON.parse(sessionStorage.getItem('userSession'));
if (!userSession || userSession.userType !== 'resident') {
    window.location.href = 'index.html';
}

let residentData = null;
let complaintsData = [];

// Display user info
document.getElementById('userInfo').textContent = `ID: ${userSession.userId}`;

// Navigation
document.querySelectorAll('.nav-item').forEach(item => {
    item.addEventListener('click', (e) => {
        e.preventDefault();
        const section = item.dataset.section;
        
        // Update active nav
        document.querySelectorAll('.nav-item').forEach(nav => nav.classList.remove('active'));
        item.classList.add('active');
        
        // Update active section
        document.querySelectorAll('.content-section').forEach(sec => sec.classList.remove('active'));
        document.getElementById(section).classList.add('active');
        
        // Update title
        document.getElementById('sectionTitle').textContent = 
            item.textContent.trim().replace(/^[^\w]+/, '');
        
        // Load data for section
        loadSectionData(section);
    });
});

// Logout
document.getElementById('logoutBtn').addEventListener('click', () => {
    sessionStorage.removeItem('userSession');
    window.location.href = 'index.html';
});

// Load section data
async function loadSectionData(section) {
    switch(section) {
        case 'dashboard':
            loadDashboard();
            break;
        case 'my-complaints':
            loadMyComplaints();
            break;
        case 'history':
            loadHistory();
            break;
        case 'profile':
            loadProfile();
            break;
    }
}

// Load resident details
async function loadResidentDetails() {
    try {
        const response = await fetch(`${API_BASE_URL}/resident/getDetails?UniqueId=${userSession.userId}`);
        residentData = await response.json();
        return residentData;
    } catch (error) {
        console.error('Error loading resident details:', error);
        return null;
    }
}

// Load dashboard
async function loadDashboard() {
    await loadResidentDetails();
    
    // Simulate complaint statistics (will be replaced with actual API calls)
    complaintsData = []; // This would come from the workflow service
    
    const active = complaintsData.filter(c => c.status !== 'RESOLVED').length;
    const pending = complaintsData.filter(c => c.status.includes('PENDING')).length;
    const resolved = complaintsData.filter(c => c.status === 'RESOLVED').length;
    
    document.getElementById('activeComplaints').textContent = active;
    document.getElementById('pendingComplaints').textContent = pending;
    document.getElementById('resolvedComplaints').textContent = resolved;
    document.getElementById('totalComplaints').textContent = complaintsData.length;
    
    displayRecentComplaints();
}

function displayRecentComplaints() {
    const list = document.getElementById('recentComplaintsList');
    const recent = complaintsData.slice(0, 5);
    
    if (recent.length === 0) {
        list.innerHTML = '<p style="padding: 20px;">No complaints yet. Lodge your first complaint!</p>';
        return;
    }
    
    list.innerHTML = recent.map(complaint => `
        <div class="data-item">
            <div class="data-info">
                <h4>${complaint.subject}</h4>
                <p>${complaint.description}</p>
                <p><span class="status-badge status-${getStatusClass(complaint.status)}">${complaint.status}</span></p>
            </div>
            <div class="data-actions">
                <button class="btn-secondary" onclick="viewComplaint(${complaint.id})">View</button>
            </div>
        </div>
    `).join('');
}

// Lodge Complaint
document.getElementById('complaintForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const complaintData = {
        residentId: userSession.userId,
        type: document.getElementById('complaintType').value,
        subject: document.getElementById('complaintSubject').value,
        description: document.getElementById('complaintDescription').value,
        priority: document.getElementById('complaintPriority').value,
        location: document.getElementById('complaintLocation').value,
        status: 'PENDING_BM'
    };
    
    try {
        // This endpoint will need to be added to your workflow service
        const response = await fetch(`${API_BASE_URL}/workflow/createComplaint`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(complaintData)
        });
        
        const statusDiv = document.getElementById('complaintStatus');
        
        if (response.ok) {
            statusDiv.textContent = 'Complaint lodged successfully! We will assign a technician soon.';
            statusDiv.className = 'status-message success';
            document.getElementById('complaintForm').reset();
            
            setTimeout(() => {
                statusDiv.className = 'status-message';
            }, 5000);
            
            loadDashboard();
        } else {
            statusDiv.textContent = 'Failed to lodge complaint. Please try again.';
            statusDiv.className = 'status-message error';
        }
    } catch (error) {
        console.error('Error lodging complaint:', error);
        const statusDiv = document.getElementById('complaintStatus');
        statusDiv.textContent = 'Error lodging complaint. Please check your connection.';
        statusDiv.className = 'status-message error';
    }
});

// Load My Complaints
async function loadMyComplaints() {
    try {
        // This endpoint will need to be added to your workflow service
        const response = await fetch(`${API_BASE_URL}/workflow/getComplaintsByResident?residentId=${userSession.userId}`);
        complaintsData = await response.json();
        displayMyComplaints(complaintsData);
    } catch (error) {
        console.error('Error loading complaints:', error);
        document.getElementById('myComplaintsList').innerHTML = 
            '<p style="padding: 20px;">Unable to load complaints. This feature requires workflow service API integration.</p>';
    }
}

function displayMyComplaints(complaints) {
    const list = document.getElementById('myComplaintsList');
    
    if (!complaints || complaints.length === 0) {
        list.innerHTML = '<p style="padding: 20px;">No complaints found</p>';
        return;
    }
    
    list.innerHTML = complaints.map(complaint => `
        <div class="data-item">
            <div class="data-info">
                <h4>${complaint.subject}</h4>
                <p>${complaint.description}</p>
                <p>Type: ${complaint.type} | Priority: ${complaint.priority} | Location: ${complaint.location}</p>
                <p>Created: ${new Date(complaint.createdAt).toLocaleString()}</p>
                <p><span class="status-badge status-${getStatusClass(complaint.status)}">${formatStatus(complaint.status)}</span></p>
            </div>
            <div class="data-actions">
                <button class="btn-secondary" onclick="viewComplaint(${complaint.id})">View Details</button>
                ${complaint.status === 'JC_TECHNICIAN' ? `<button class="btn-primary" onclick="closeComplaint(${complaint.id})">Close from My Side</button>` : ''}
            </div>
        </div>
    `).join('');
}

// Status filter
document.getElementById('statusFilter').addEventListener('change', (e) => {
    const status = e.target.value;
    if (status) {
        const filtered = complaintsData.filter(c => c.status === status);
        displayMyComplaints(filtered);
    } else {
        displayMyComplaints(complaintsData);
    }
});

// View complaint details
const complaintDetailModal = document.getElementById('complaintDetailModal');
complaintDetailModal.querySelector('.close').addEventListener('click', () => {
    complaintDetailModal.classList.remove('show');
});

async function viewComplaint(complaintId) {
    try {
        // This endpoint will need to be added to your workflow service
        const response = await fetch(`${API_BASE_URL}/workflow/getComplaint?complaintId=${complaintId}`);
        const complaint = await response.json();
        
        const content = document.getElementById('complaintDetailContent');
        content.innerHTML = `
            <div class="complaint-details">
                <p><strong>Subject:</strong> ${complaint.subject}</p>
                <p><strong>Description:</strong> ${complaint.description}</p>
                <p><strong>Type:</strong> ${complaint.type}</p>
                <p><strong>Priority:</strong> ${complaint.priority}</p>
                <p><strong>Location:</strong> ${complaint.location}</p>
                <p><strong>Status:</strong> <span class="status-badge status-${getStatusClass(complaint.status)}">${formatStatus(complaint.status)}</span></p>
                <p><strong>Created:</strong> ${new Date(complaint.createdAt).toLocaleString()}</p>
                ${complaint.technician ? `<p><strong>Assigned Technician:</strong> ${complaint.technician.name}</p>` : ''}
                ${complaint.updatedAt ? `<p><strong>Last Updated:</strong> ${new Date(complaint.updatedAt).toLocaleString()}</p>` : ''}
            </div>
        `;
        
        const actions = document.getElementById('complaintActions');
        if (complaint.status === 'JC_TECHNICIAN') {
            actions.innerHTML = `
                <button class="btn-primary" onclick="closeComplaint(${complaint.id})">Close from My Side</button>
            `;
        } else {
            actions.innerHTML = '';
        }
        
        complaintDetailModal.classList.add('show');
    } catch (error) {
        console.error('Error loading complaint details:', error);
        alert('Error loading complaint details');
    }
}

async function closeComplaint(complaintId) {
    if (!confirm('Are you sure you want to close this complaint from your side?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/workflow/closeComplaint`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ complaintId, closedBy: 'RESIDENT' })
        });
        
        if (response.ok) {
            alert('Complaint closed successfully!');
            complaintDetailModal.classList.remove('show');
            loadMyComplaints();
            loadDashboard();
        } else {
            alert('Failed to close complaint');
        }
    } catch (error) {
        console.error('Error closing complaint:', error);
        alert('Error closing complaint');
    }
}

// Load History
async function loadHistory() {
    try {
        const response = await fetch(`${API_BASE_URL}/workflow/getComplaintHistory?residentId=${userSession.userId}`);
        const history = await response.json();
        displayHistory(history);
    } catch (error) {
        console.error('Error loading history:', error);
        document.getElementById('historyList').innerHTML = 
            '<p style="padding: 20px;">Unable to load history. This feature requires workflow service API integration.</p>';
    }
}

function displayHistory(history) {
    const list = document.getElementById('historyList');
    
    if (!history || history.length === 0) {
        list.innerHTML = '<p style="padding: 20px;">No history found</p>';
        return;
    }
    
    list.innerHTML = history.map(item => `
        <div class="data-item">
            <div class="data-info">
                <h4>${item.subject}</h4>
                <p>${item.description}</p>
                <p>Type: ${item.type} | Status: ${formatStatus(item.status)}</p>
                <p>Created: ${new Date(item.createdAt).toLocaleString()} | Resolved: ${item.resolvedAt ? new Date(item.resolvedAt).toLocaleString() : 'N/A'}</p>
            </div>
        </div>
    `).join('');
}

// Load Profile
async function loadProfile() {
    const details = await loadResidentDetails();
    
    if (!details) {
        document.getElementById('profileDetails').innerHTML = 
            '<p>Unable to load profile details</p>';
        return;
    }
    
    document.getElementById('profileDetails').innerHTML = `
        <div class="profile-info">
            <p><strong>Unique ID:</strong> ${details.uniqueId || 'N/A'}</p>
            <p><strong>Name:</strong> ${details.name || 'N/A'}</p>
            <p><strong>Email:</strong> ${details.email || 'N/A'}</p>
            <p><strong>Phone:</strong> ${details.phone || 'N/A'}</p>
            <p><strong>Block:</strong> ${details.block || 'N/A'}</p>
            <p><strong>Flat Number:</strong> ${details.flatNumber || 'N/A'}</p>
        </div>
    `;
}

// Helper functions
function getStatusClass(status) {
    if (status.includes('PENDING')) return 'pending';
    if (status.includes('JC')) return 'progress';
    if (status === 'RESOLVED') return 'resolved';
    return 'pending';
}

function formatStatus(status) {
    return status.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
}

// Initialize
loadDashboard();