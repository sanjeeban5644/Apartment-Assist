const API_BASE_URL = 'http://localhost:9021';

// Check authentication
const userSession = JSON.parse(sessionStorage.getItem('userSession'));
if (!userSession || userSession.userType !== 'technician') {
    window.location.href = 'index.html';
}

let technicianData = null;
let jobsData = [];
let currentJobId = null;

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
        case 'pending-jobs':
            loadPendingJobs();
            break;
        case 'my-jobs':
            loadMyJobs();
            break;
        case 'completed':
            loadCompletedJobs();
            break;
        case 'profile':
            loadProfile();
            break;
    }
}

// Load technician details
async function loadTechnicianDetails() {
    try {
        const response = await fetch(`${API_BASE_URL}/admin/getTechnician?technicianId=${userSession.userId}`);
        technicianData = await response.json();
        return technicianData;
    } catch (error) {
        console.error('Error loading technician details:', error);
        return null;
    }
}

// Load dashboard
async function loadDashboard() {
    await loadTechnicianDetails();
    
    try {
        // These endpoints will need to be added to your workflow service
        const response = await fetch(`${API_BASE_URL}/workflow/getTechnicianJobs?technicianId=${userSession.userId}`);
        jobsData = await response.json();
        
        const pending = jobsData.filter(j => j.status === 'PENDING').length;
        const active = jobsData.filter(j => j.status === 'ACCEPTED' || j.status === 'IN_PROGRESS').length;
        const completedToday = jobsData.filter(j => {
            if (j.status === 'COMPLETED' && j.completedAt) {
                const completedDate = new Date(j.completedAt);
                const today = new Date();
                return completedDate.toDateString() === today.toDateString();
            }
            return false;
        }).length;
        const totalCompleted = jobsData.filter(j => j.status === 'COMPLETED').length;
        
        document.getElementById('pendingJobs').textContent = pending;
        document.getElementById('activeJobs').textContent = active;
        document.getElementById('completedToday').textContent = completedToday;
        document.getElementById('totalCompleted').textContent = totalCompleted;
        
        displayRecentJobs();
    } catch (error) {
        console.error('Error loading dashboard:', error);
        document.getElementById('recentJobsList').innerHTML = 
            '<p style="padding: 20px;">Unable to load jobs. This feature requires workflow service API integration.</p>';
    }
}

function displayRecentJobs() {
    const list = document.getElementById('recentJobsList');
    const recent = jobsData.slice(0, 5);
    
    if (recent.length === 0) {
        list.innerHTML = '<p style="padding: 20px;">No jobs assigned yet</p>';
        return;
    }
    
    list.innerHTML = recent.map(job => `
        <div class="data-item">
            <div class="data-info">
                <h4>${job.complaint.subject}</h4>
                <p>${job.complaint.description}</p>
                <p>Type: ${job.complaint.type} | Priority: ${job.complaint.priority}</p>
                <p><span class="status-badge status-${getStatusClass(job.status)}">${job.status}</span></p>
            </div>
            <div class="data-actions">
                <button class="btn-secondary" onclick="viewJob(${job.id})">View</button>
            </div>
        </div>
    `).join('');
}

// Load Pending Jobs
async function loadPendingJobs() {
    try {
        const response = await fetch(`${API_BASE_URL}/workflow/getPendingJobsForTechnician?technicianId=${userSession.userId}`);
        const pendingJobs = await response.json();
        displayPendingJobs(pendingJobs);
    } catch (error) {
        console.error('Error loading pending jobs:', error);
        document.getElementById('pendingJobsList').innerHTML = 
            '<p style="padding: 20px;">Unable to load pending jobs. This feature requires workflow service API integration.</p>';
    }
}

function displayPendingJobs(jobs) {
    const list = document.getElementById('pendingJobsList');
    
    if (!jobs || jobs.length === 0) {
        list.innerHTML = '<p style="padding: 20px;">No pending job requests</p>';
        return;
    }
    
    list.innerHTML = jobs.map(job => `
        <div class="data-item">
            <div class="data-info">
                <h4>${job.complaint.subject}</h4>
                <p>${job.complaint.description}</p>
                <p>Type: ${job.complaint.type} | Priority: ${job.complaint.priority} | Location: ${job.complaint.location}</p>
                <p>Resident: ${job.resident.name} | Block: ${job.resident.block} | Flat: ${job.resident.flatNumber}</p>
                <p>Contact: ${job.resident.phone}</p>
            </div>
            <div class="data-actions">
                <button class="btn-primary" onclick="acceptJob(${job.id})">Accept</button>
                <button class="btn-danger" onclick="rejectJob(${job.id})">Reject</button>
            </div>
        </div>
    `).join('');
}

async function acceptJob(jobId) {
    try {
        const response = await fetch(`${API_BASE_URL}/workflow/acceptJob`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ jobId, technicianId: userSession.userId })
        });
        
        if (response.ok) {
            alert('Job accepted successfully!');
            loadPendingJobs();
            loadDashboard();
        } else {
            alert('Failed to accept job');
        }
    } catch (error) {
        console.error('Error accepting job:', error);
        alert('Error accepting job');
    }
}

async function rejectJob(jobId) {
    const reason = prompt('Please provide a reason for rejection:');
    if (!reason) return;
    
    try {
        const response = await fetch(`${API_BASE_URL}/workflow/rejectJob`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ jobId, technicianId: userSession.userId, reason })
        });
        
        if (response.ok) {
            alert('Job rejected');
            loadPendingJobs();
            loadDashboard();
        } else {
            alert('Failed to reject job');
        }
    } catch (error) {
        console.error('Error rejecting job:', error);
        alert('Error rejecting job');
    }
}

// Load My Jobs
async function loadMyJobs() {
    try {
        const response = await fetch(`${API_BASE_URL}/workflow/getActiveJobsForTechnician?technicianId=${userSession.userId}`);
        const myJobs = await response.json();
        displayMyJobs(myJobs);
    } catch (error) {
        console.error('Error loading my jobs:', error);
        document.getElementById('myJobsList').innerHTML = 
            '<p style="padding: 20px;">Unable to load jobs. This feature requires workflow service API integration.</p>';
    }
}

function displayMyJobs(jobs) {
    const list = document.getElementById('myJobsList');
    
    if (!jobs || jobs.length === 0) {
        list.innerHTML = '<p style="padding: 20px;">No active jobs</p>';
        return;
    }
    
    list.innerHTML = jobs.map(job => `
        <div class="data-item">
            <div class="data-info">
                <h4>${job.complaint.subject}</h4>
                <p>${job.complaint.description}</p>
                <p>Type: ${job.complaint.type} | Priority: ${job.complaint.priority} | Location: ${job.complaint.location}</p>
                <p>Resident: ${job.resident.name} | Block: ${job.resident.block} | Flat: ${job.resident.flatNumber}</p>
                <p>Contact: ${job.resident.phone}</p>
                <p><span class="status-badge status-${getStatusClass(job.status)}">${job.status}</span></p>
            </div>
            <div class="data-actions">
                <button class="btn-secondary" onclick="viewJob(${job.id})">View Details</button>
                <button class="btn-primary" onclick="openUpdateStatus(${job.id})">Update Status</button>
            </div>
        </div>
    `).join('');
}

// Job status filter
document.getElementById('jobStatusFilter').addEventListener('change', async (e) => {
    const status = e.target.value;
    try {
        let url = `${API_BASE_URL}/workflow/getActiveJobsForTechnician?technicianId=${userSession.userId}`;
        if (status) {
            url += `&status=${status}`;
        }
        const response = await fetch(url);
        const jobs = await response.json();
        displayMyJobs(jobs);
    } catch (error) {
        console.error('Error filtering jobs:', error);
    }
});

// Job Detail Modal
const jobDetailModal = document.getElementById('jobDetailModal');
jobDetailModal.querySelector('.close').addEventListener('click', () => {
    jobDetailModal.classList.remove('show');
});

async function viewJob(jobId) {
    try {
        const response = await fetch(`${API_BASE_URL}/workflow/getJob?jobId=${jobId}`);
        const job = await response.json();
        
        const content = document.getElementById('jobDetailContent');
        content.innerHTML = `
            <div class="job-details">
                <h3>Complaint Details</h3>
                <p><strong>Subject:</strong> ${job.complaint.subject}</p>
                <p><strong>Description:</strong> ${job.complaint.description}</p>
                <p><strong>Type:</strong> ${job.complaint.type}</p>
                <p><strong>Priority:</strong> ${job.complaint.priority}</p>
                <p><strong>Location:</strong> ${job.complaint.location}</p>
                
                <h3 style="margin-top: 20px;">Resident Details</h3>
                <p><strong>Name:</strong> ${job.resident.name}</p>
                <p><strong>Block:</strong> ${job.resident.block}</p>
                <p><strong>Flat:</strong> ${job.resident.flatNumber}</p>
                <p><strong>Phone:</strong> ${job.resident.phone}</p>
                <p><strong>Email:</strong> ${job.resident.email}</p>
                
                <h3 style="margin-top: 20px;">Job Status</h3>
                <p><strong>Current Status:</strong> <span class="status-badge status-${getStatusClass(job.status)}">${job.status}</span></p>
                <p><strong>Assigned On:</strong> ${new Date(job.assignedAt).toLocaleString()}</p>
                ${job.acceptedAt ? `<p><strong>Accepted On:</strong> ${new Date(job.acceptedAt).toLocaleString()}</p>` : ''}
                ${job.completedAt ? `<p><strong>Completed On:</strong> ${new Date(job.completedAt).toLocaleString()}</p>` : ''}
            </div>
        `;
        
        const actions = document.getElementById('jobActions');
        if (job.status === 'ACCEPTED' || job.status === 'IN_PROGRESS') {
            actions.innerHTML = `
                <button class="btn-primary" onclick="openUpdateStatus(${job.id})">Update Status</button>
            `;
        } else {
            actions.innerHTML = '';
        }
        
        jobDetailModal.classList.add('show');
    } catch (error) {
        console.error('Error loading job details:', error);
        alert('Error loading job details');
    }
}

// Update Status Modal
const updateStatusModal = document.getElementById('updateStatusModal');
updateStatusModal.querySelector('.close').addEventListener('click', () => {
    updateStatusModal.classList.remove('show');
});

function openUpdateStatus(jobId) {
    currentJobId = jobId;
    updateStatusModal.classList.add('show');
}

document.getElementById('updateStatusForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const statusData = {
        jobId: currentJobId,
        status: document.getElementById('jobStatus').value,
        notes: document.getElementById('statusNotes').value,
        technicianId: userSession.userId
    };
    
    try {
        const response = await fetch(`${API_BASE_URL}/workflow/updateJobStatus`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(statusData)
        });
        
        if (response.ok) {
            alert('Status updated successfully!');
            updateStatusModal.classList.remove('show');
            document.getElementById('updateStatusForm').reset();
            loadMyJobs();
            loadDashboard();
        } else {
            alert('Failed to update status');
        }
    } catch (error) {
        console.error('Error updating status:', error);
        alert('Error updating status');
    }
});

// Load Completed Jobs
async function loadCompletedJobs() {
    try {
        const filter = document.getElementById('completedFilter').value;
        const response = await fetch(`${API_BASE_URL}/workflow/getCompletedJobsForTechnician?technicianId=${userSession.userId}&filter=${filter}`);
        const completedJobs = await response.json();
        displayCompletedJobs(completedJobs);
    } catch (error) {
        console.error('Error loading completed jobs:', error);
        document.getElementById('completedJobsList').innerHTML = 
            '<p style="padding: 20px;">Unable to load completed jobs. This feature requires workflow service API integration.</p>';
    }
}

function displayCompletedJobs(jobs) {
    const list = document.getElementById('completedJobsList');
    
    if (!jobs || jobs.length === 0) {
        list.innerHTML = '<p style="padding: 20px;">No completed jobs</p>';
        return;
    }
    
    list.innerHTML = jobs.map(job => `
        <div class="data-item">
            <div class="data-info">
                <h4>${job.complaint.subject}</h4>
                <p>${job.complaint.description}</p>
                <p>Type: ${job.complaint.type} | Resident: ${job.resident.name}</p>
                <p>Completed: ${new Date(job.completedAt).toLocaleString()}</p>
                <p><span class="status-badge status-completed">Completed</span></p>
            </div>
            <div class="data-actions">
                <button class="btn-secondary" onclick="viewJob(${job.id})">View Details</button>
            </div>
        </div>
    `).join('');
}

// Completed filter
document.getElementById('completedFilter').addEventListener('change', () => {
    loadCompletedJobs();
});

// Load Profile
async function loadProfile() {
    const details = await loadTechnicianDetails();
    
    if (!details) {
        document.getElementById('profileDetails').innerHTML = 
            '<p>Unable to load profile details</p>';
        return;
    }
    
    document.getElementById('profileDetails').innerHTML = `
        <div class="profile-info">
            <p><strong>Technician ID:</strong> ${details.id || 'N/A'}</p>
            <p><strong>Name:</strong> ${details.name || 'N/A'}</p>
            <p><strong>Email:</strong> ${details.email || 'N/A'}</p>
            <p><strong>Phone:</strong> ${details.phone || 'N/A'}</p>
            <p><strong>Specialization:</strong> ${details.specialization || 'N/A'}</p>
            <p><strong>Assigned Block:</strong> ${details.assignedBlock || 'N/A'}</p>
        </div>
    `;
}

// Helper functions
function getStatusClass(status) {
    if (status === 'PENDING') return 'pending';
    if (status === 'ACCEPTED' || status === 'IN_PROGRESS') return 'progress';
    if (status === 'COMPLETED') return 'completed';
    return 'pending';
}

// Initialize
loadDashboard();