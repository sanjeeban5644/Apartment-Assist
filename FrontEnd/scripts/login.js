const API_BASE_URL = 'http://localhost:8080'; // Change to your API gateway URL

document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const userType = document.getElementById('userType').value;
    const userId = document.getElementById('userId').value;
    const password = document.getElementById('password').value;
    const errorMessage = document.getElementById('errorMessage');
    
    errorMessage.classList.remove('show');
    
    if (!userType || !userId || !password) {
        showError('Please fill in all fields');
        return;
    }
    
    try {
        // In a real application, you would validate credentials with your backend
        // For now, we'll simulate login based on user type
        
        const loginData = {
            userType: userType,
            userId: userId,
            password: password,
            timestamp: new Date().toISOString()
        };
        
        // Store user session
        sessionStorage.setItem('userSession', JSON.stringify(loginData));
        
        // Redirect based on user type
        switch(userType) {
            case 'admin':
                window.location.href = 'admin-dashboard.html';
                break;
            case 'resident':
                window.location.href = 'resident-dashboard.html';
                break;
            case 'technician':
                window.location.href = 'technician-dashboard.html';
                break;
            default:
                showError('Invalid user type');
        }
        
    } catch (error) {
        showError('Login failed. Please try again.');
        console.error('Login error:', error);
    }
});

function showError(message) {
    const errorMessage = document.getElementById('errorMessage');
    errorMessage.textContent = message;
    errorMessage.classList.add('show');
}