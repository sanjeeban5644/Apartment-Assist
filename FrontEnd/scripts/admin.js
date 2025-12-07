const API_BASE_URL = 'http://localhost:9021';

// Check authentication
const userSession = JSON.parse(sessionStorage.getItem('userSession'));
if (!userSession || userSession.userType !== 'admin') {
    window.location.href = 'index.html';
}

// Display user info
document.getElementById('userInfo').textContent = `Logged in as: ${userSession.userId}`;

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
        //loadSectionData(section);
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
        case 'overview':
            loadOverview();
            break;
        case 'managers':
            loadManagers();
            break;
        case 'residents':
            loadResidents();
            break;
        case 'technicians':
            loadTechnicians();
            break;
        case 'complaints':
            loadComplaints();
            break;
    }
}

// Load overview stats
async function loadOverview() {
    try {
        const [residents, technicians, managers] = await Promise.all([
            fetch(`${API_BASE_URL}/admin/getAllResidents`).then(r => r.json()),
            fetch(`${API_BASE_URL}/admin/getTechnician`).then(r => r.json()),
            fetch(`${API_BASE_URL}/admin/getManagers`).then(r => r.json())
        ]);
        
        document.getElementById('totalResidents').textContent = residents.length || 0;
        document.getElementById('totalTechnicians').textContent = technicians.length || 0;
        document.getElementById('totalManagers').textContent = managers.length || 0;
        document.getElementById('activeComplaints').textContent = '0'; // Will be updated when complaints API is available
    } catch (error) {
        console.error('Error loading overview:', error);
    }
}

// MANAGERS SECTION
let managersData = [];

async function loadManagers() {
    try {
        const response = await fetch(`${API_BASE_URL}/admin/getManagers`);
        managersData = await response.json();
        displayManagers(managersData);
    } catch (error) {
        console.error('Error loading managers:', error);
        document.getElementById('managersList').innerHTML = 
            '<p style="padding: 20px;">Error loading managers</p>';
    }
}

function displayManagers(managers) {
    const list = document.getElementById('managersList');
    if (!managers || managers.length === 0) {
        list.innerHTML = '<p style="padding: 20px;">No managers found</p>';
        return;
    }
    
    list.innerHTML = managers.map(manager => `
        <div class="data-item">
            <div class="data-info">
                <h4>${manager.name || 'N/A'}</h4>
                <p>Type: ${manager.type || 'N/A'} | Email: ${manager.email || 'N/A'} | Phone: ${manager.phone || 'N/A'}</p>
                ${manager.blockNumber ? `<p>Block: ${manager.blockNumber}</p>` : ''}
            </div>
            <div class="data-actions">
                <button class="btn-secondary" onclick="viewManager(${manager.id})">View</button>
            </div>
        </div>
    `).join('');
}

// Manager modal
const managerModal = document.getElementById('managerModal');
document.getElementById('addManagerBtn').addEventListener('click', () => {
    document.getElementById('managerForm').reset();
    managerModal.classList.add('show');
});

managerModal.querySelector('.close').addEventListener('click', () => {
    managerModal.classList.remove('show');
});

document.getElementById('managerType').addEventListener('change', (e) => {
    const blockFieldGroup = document.getElementById('blockFieldGroup');
    if (e.target.value === 'BM') {
        blockFieldGroup.style.display = 'block';
    } else {
        blockFieldGroup.style.display = 'none';
    }
});

document.getElementById('managerForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const managerData = {
        type: document.getElementById('managerType').value,
        name: document.getElementById('managerName').value,
        email: document.getElementById('managerEmail').value,
        phone: document.getElementById('managerPhone').value,
        blockNumber: document.getElementById('blockNumber').value
    };
    
    try {
        const response = await fetch(`${API_BASE_URL}/admin/createManager`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(managerData)
        });
        
        if (response.ok) {
            alert('Manager created successfully!');
            managerModal.classList.remove('show');
            loadManagers();
        } else {
            alert('Failed to create manager');
        }
    } catch (error) {
        console.error('Error creating manager:', error);
        alert('Error creating manager');
    }
});

// RESIDENTS SECTION
let residentsData = [];
let isSearchMode = false;

async function loadResidents() {
    try {

        const response = await fetch(`${API_BASE_URL}/admin/getAllResidents`);
        residentsData = await response.json();
        
        // Only display if not in search mode
        if (!isSearchMode) {
            displayResidents(residentsData, false);
        }
    } catch (error) {
        console.error('Error loading residents:', error);
        document.getElementById('residentsList').innerHTML = 
            '<p style="padding: 20px;">Error loading residents</p>';
    }
}

function displayResidents(residents, showSearchInfo) {
    const list = document.getElementById('residentsList');
    const searchResults = document.getElementById('searchResults');
    
    if (!residents || residents.length === 0) {
        list.innerHTML = '<p style="padding: 20px;">No residents found</p>';
        searchResults.style.display = 'none';
        return;
    }
    
    // Show search result info if in search mode
    if (showSearchInfo) {
        const searchValue = document.getElementById('searchResident').value;
        searchResults.textContent = `Found ${residents.length} resident(s) matching "${searchValue}"`;
        searchResults.style.display = 'block';
    } else {
        searchResults.style.display = 'none';
    }
    
    const residentsHTML = residents.map(resident => `
        <div class="data-item">
            <div class="data-info">
                <h4>${resident.firstName || 'N/A'}</h4>
                <p>ID: ${resident.residentUniqueId || 'N/A'} | Email: ${resident.email || 'N/A'} | Phone: ${resident.phone || 'N/A'}</p>
                <p>Block: ${resident.block || 'N/A'} | Flat: ${resident.flatNumber || 'N/A'}</p>
            </div>
            <div class="data-actions">
                <button class="btn-secondary" onclick="downloadResidentPDF('${resident.uniqueId}')">PDF</button>
                <button class="btn-secondary" onclick="editResident('${resident.uniqueId}')">Edit</button>
            </div>
        </div>
    `).join('');
    
    list.innerHTML = residentsHTML;
}

// Search functionality - completely separate from loadResidents
function searchResidents() {
    const searchTerm = document.getElementById('searchResident').value.toLowerCase().trim();
    
    if (searchTerm === '') {
        alert('Please enter a search term');
        return;
    }
    
    // If data not loaded yet, load it first
    if (residentsData.length === 0) {
        alert('Loading residents data, please try again in a moment');
        loadResidents();
        return;
    }
    
    // Set search mode
    isSearchMode = true;
    
    // Perform search
    const searchResults = residentsData.filter(resident => {
        return (
            (resident.firstName && resident.firstName.toLowerCase().includes(searchTerm)) ||
            (resident.lastName && resident.lastName.toLowerCase().includes(searchTerm)) ||
            (resident.blockNumber && resident.blockNumber.toLowerCase().includes(searchTerm)) ||
            (resident.floorNumber && resident.floorNumber.toLowerCase().includes(searchTerm)) ||
            (resident.apartmentNumber && resident.apartmentNumber.toLowerCase().includes(searchTerm)) ||
            (resident.familyNumber && resident.familyNumber.toLowerCase().includes(searchTerm))
        );
    });
    
    // Display search results
    displayResidents(searchResults, true);
}

// Search button click event
document.getElementById('searchBtn')?.addEventListener('click', searchResidents);

// View All button - resets to show all residents
document.getElementById('viewAllResidents')?.addEventListener('click', () => {
    // Clear search input
    document.getElementById('searchResident').value = '';
    
    // Exit search mode
    isSearchMode = false;
    
    // Display all residents
    displayResidents(residentsData, false);
});

// Resident modal
const residentModal = document.getElementById('residentModal');
document.getElementById('addResidentBtn').addEventListener('click', () => {
    document.getElementById('residentForm').reset();
    residentModal.classList.add('show');
});

residentModal.querySelector('.close').addEventListener('click', () => {
    residentModal.classList.remove('show');
});

document.getElementById('residentForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const residentData = {
        uniqueId: document.getElementById('residentUniqueId').value,
        name: document.getElementById('residentName').value,
        email: document.getElementById('residentEmail').value,
        phone: document.getElementById('residentPhone').value,
        block: document.getElementById('residentBlock').value,
        flatNumber: document.getElementById('residentFlat').value
    };
    
    try {
        const response = await fetch(`${API_BASE_URL}/admin/createOrUpdateResident`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(residentData)
        });
        
        if (response.ok) {
            alert('Resident saved successfully!');
            residentModal.classList.remove('show');
            loadResidents();
        } else {
            alert('Failed to save resident');
        }
    } catch (error) {
        console.error('Error saving resident:', error);
        alert('Error saving resident');
    }
});

async function downloadResidentPDF(uniqueId) {
    try {
        window.open(`${API_BASE_URL}/admin/getResidentDetails?UniqueId=${uniqueId}`, '_blank');
    } catch (error) {
        console.error('Error downloading PDF:', error);
        alert('Error downloading PDF');
    }
}

async function editResident(uniqueId) {
    try {
        const response = await fetch(`${API_BASE_URL}/admin/getResidentDetailsByUniqueId?ResidentUniqueId=${uniqueId}`);
        const resident = await response.json();
        
        document.getElementById('residentUniqueId').value = resident.uniqueId || '';
        document.getElementById('residentName').value = resident.name || '';
        document.getElementById('residentEmail').value = resident.email || '';
        document.getElementById('residentPhone').value = resident.phone || '';
        document.getElementById('residentBlock').value = resident.block || '';
        document.getElementById('residentFlat').value = resident.flatNumber || '';
        
        residentModal.classList.add('show');
    } catch (error) {
        console.error('Error loading resident:', error);
        alert('Error loading resident details');
    }
}

// TECHNICIANS SECTION
let techniciansData = [];

async function loadTechnicians() {
    try {
        const response = await fetch(`${API_BASE_URL}/admin/getTechnician`);
        techniciansData = await response.json();
        displayTechnicians(techniciansData);
    } catch (error) {
        console.error('Error loading technicians:', error);
        document.getElementById('techniciansList').innerHTML = 
            '<p style="padding: 20px;">Error loading technicians</p>';
    }
}

function displayTechnicians(technicians) {
    const list = document.getElementById('techniciansList');
    if (!technicians || technicians.length === 0) {
        list.innerHTML = '<p style="padding: 20px;">No technicians found</p>';
        return;
    }
    
    list.innerHTML = technicians.map(tech => `
        <div class="data-item">
            <div class="data-info">
                <h4>${tech.name || 'N/A'}</h4>
                <p>Email: ${tech.email || 'N/A'} | Phone: ${tech.phone || 'N/A'}</p>
                <p>Specialization: ${tech.specialization || 'N/A'} | Block: ${tech.assignedBlock || 'N/A'}</p>
            </div>
            <div class="data-actions">
                <button class="btn-secondary" onclick="viewTechnician(${tech.id})">View</button>
            </div>
        </div>
    `).join('');
}

// Technician modal
const technicianModal = document.getElementById('technicianModal');
document.getElementById('addTechnicianBtn').addEventListener('click', () => {
    document.getElementById('technicianForm').reset();
    technicianModal.classList.add('show');
});

technicianModal.querySelector('.close').addEventListener('click', () => {
    technicianModal.classList.remove('show');
});

document.getElementById('technicianForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const technicianData = {
        name: document.getElementById('technicianName').value,
        email: document.getElementById('technicianEmail').value,
        phone: document.getElementById('technicianPhone').value,
        specialization: document.getElementById('technicianSpecialization').value,
        assignedBlock: document.getElementById('technicianBlock').value
    };
    
    try {
        const response = await fetch(`${API_BASE_URL}/admin/createTechnician`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(technicianData)
        });
        
        if (response.ok) {
            alert('Technician created successfully!');
            technicianModal.classList.remove('show');
            loadTechnicians();
        } else {
            alert('Failed to create technician');
        }
    } catch (error) {
        console.error('Error creating technician:', error);
        alert('Error creating technician');
    }
});

// COMPLAINTS SECTION
async function loadComplaints() {
    // This will be implemented when complaint endpoints are available
    document.getElementById('complaintsList').innerHTML = 
        '<p style="padding: 20px;">Complaints section will be available when workflow service APIs are integrated</p>';
}

// FILE UPLOAD SECTION
document.getElementById('excelFile').addEventListener('change', (e) => {
    const fileName = e.target.files[0]?.name || 'No file chosen';
    document.getElementById('fileName').textContent = fileName;
});

document.getElementById('uploadForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const fileInput = document.getElementById('excelFile');
    const file = fileInput.files[0];
    
    if (!file) {
        alert('Please select a file');
        return;
    }
    
    const formData = new FormData();
    formData.append('file', file);
    
    try {
        const response = await fetch(`${API_BASE_URL}/admin/uploadData`, {
            method: 'POST',
            body: formData
        });
        
        const statusDiv = document.getElementById('uploadStatus');
        
        if (response.ok) {
            statusDiv.textContent = 'File uploaded successfully!';
            statusDiv.className = 'status-message success';
            fileInput.value = '';
            document.getElementById('fileName').textContent = 'No file chosen';
            loadResidents();
        } else {
            statusDiv.textContent = 'Upload failed. Please try again.';
            statusDiv.className = 'status-message error';
        }
    } catch (error) {
        console.error('Error uploading file:', error);
        const statusDiv = document.getElementById('uploadStatus');
        statusDiv.textContent = 'Error uploading file';
        statusDiv.className = 'status-message error';
    }
});

// Initialize
loadOverview();