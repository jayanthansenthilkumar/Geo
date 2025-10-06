// API Configuration
const API_CONFIG = {
    BASE_URL: 'http://localhost:8080/api',
    ENDPOINTS: {
        // Authentication
        LOGIN: '/auth/login',
        PUBLIC_LOGIN: '/auth/public/login',
        PUBLIC_REGISTER: '/auth/public/register',
        
        // Issues
        ISSUES: '/issues',
        ISSUE_BY_ID: (id) => `/issues/${id}`,
        ISSUES_BY_STATUS: (status) => `/issues/status/${status}`,
        ISSUES_BY_CATEGORY: (category) => `/issues/category/${category}`,
        ISSUE_STATISTICS: '/issues/statistics',
        ASSIGN_WORKER: (issueId, workerId) => `/issues/${issueId}/assign/${workerId}`,
        
        // Public Complaints
        COMPLAINTS: '/complaints',
        COMPLAINT_BY_ID: (id) => `/complaints/${id}`,
        COMPLAINTS_BY_STATUS: (status) => `/complaints/status/${status}`,
        COMPLAINTS_BY_USER: (userId) => `/complaints/user/${userId}`,
        APPROVE_COMPLAINT: (id) => `/complaints/${id}/approve`,
        REJECT_COMPLAINT: (id) => `/complaints/${id}/reject`,
        CONVERT_COMPLAINT: (id) => `/complaints/${id}/convert`,
        
        // Workers
        WORKERS: '/workers',
        WORKER_BY_ID: (id) => `/workers/${id}`,
        WORKERS_BY_DEPARTMENT: (dept) => `/workers/department/${dept}`,
        AVAILABLE_WORKERS: '/workers/available',
        
        // Comments
        COMMENTS_BY_ISSUE: (issueId) => `/comments/issue/${issueId}`,
        COMMENTS: '/comments'
    }
};

// API Helper Functions
class APIClient {
    constructor() {
        this.token = sessionStorage.getItem('authToken') || null;
    }

    setToken(token) {
        this.token = token;
        if (token) {
            sessionStorage.setItem('authToken', token);
        } else {
            sessionStorage.removeItem('authToken');
        }
    }

    getHeaders(includeAuth = true) {
        const headers = {
            'Content-Type': 'application/json'
        };
        if (includeAuth && this.token) {
            headers['Authorization'] = `Bearer ${this.token}`;
        }
        return headers;
    }

    async request(url, options = {}) {
        try {
            const response = await fetch(`${API_CONFIG.BASE_URL}${url}`, {
                ...options,
                headers: this.getHeaders(options.auth !== false)
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Request failed');
            }

            return data;
        } catch (error) {
            console.error('API Request Error:', error);
            throw error;
        }
    }

    // Authentication
    async login(username, password) {
        const response = await this.request(API_CONFIG.ENDPOINTS.LOGIN, {
            method: 'POST',
            body: JSON.stringify({ username, password }),
            auth: false
        });
        if (response.token) {
            this.setToken(response.token);
        }
        return response;
    }

    async publicLogin(username, password) {
        const response = await this.request(API_CONFIG.ENDPOINTS.PUBLIC_LOGIN, {
            method: 'POST',
            body: JSON.stringify({ username, password }),
            auth: false
        });
        if (response.token) {
            this.setToken(response.token);
        }
        return response;
    }

    async publicRegister(userData) {
        return await this.request(API_CONFIG.ENDPOINTS.PUBLIC_REGISTER, {
            method: 'POST',
            body: JSON.stringify(userData),
            auth: false
        });
    }

    // Issues
    async getAllIssues() {
        return await this.request(API_CONFIG.ENDPOINTS.ISSUES);
    }

    async getIssueById(id) {
        return await this.request(API_CONFIG.ENDPOINTS.ISSUE_BY_ID(id));
    }

    async createIssue(issueData) {
        return await this.request(API_CONFIG.ENDPOINTS.ISSUES, {
            method: 'POST',
            body: JSON.stringify(issueData)
        });
    }

    async updateIssue(id, issueData) {
        return await this.request(API_CONFIG.ENDPOINTS.ISSUE_BY_ID(id), {
            method: 'PUT',
            body: JSON.stringify(issueData)
        });
    }

    async deleteIssue(id) {
        return await this.request(API_CONFIG.ENDPOINTS.ISSUE_BY_ID(id), {
            method: 'DELETE'
        });
    }

    async assignWorker(issueId, workerId) {
        return await this.request(API_CONFIG.ENDPOINTS.ASSIGN_WORKER(issueId, workerId), {
            method: 'PUT'
        });
    }

    async getIssueStatistics() {
        return await this.request(API_CONFIG.ENDPOINTS.ISSUE_STATISTICS);
    }

    // Public Complaints
    async getAllComplaints() {
        return await this.request(API_CONFIG.ENDPOINTS.COMPLAINTS);
    }

    async getComplaintsByUserId(userId) {
        return await this.request(API_CONFIG.ENDPOINTS.COMPLAINTS_BY_USER(userId));
    }

    async createComplaint(complaintData, userId) {
        return await this.request(`${API_CONFIG.ENDPOINTS.COMPLAINTS}?userId=${userId}`, {
            method: 'POST',
            body: JSON.stringify(complaintData)
        });
    }

    async approveComplaint(id, reviewerId) {
        return await this.request(`${API_CONFIG.ENDPOINTS.APPROVE_COMPLAINT(id)}?reviewerId=${reviewerId}`, {
            method: 'PUT'
        });
    }

    async rejectComplaint(id, reviewerId, reason) {
        return await this.request(`${API_CONFIG.ENDPOINTS.REJECT_COMPLAINT(id)}?reviewerId=${reviewerId}`, {
            method: 'PUT',
            body: JSON.stringify({ reason })
        });
    }

    async convertComplaintToIssue(id) {
        return await this.request(API_CONFIG.ENDPOINTS.CONVERT_COMPLAINT(id), {
            method: 'POST'
        });
    }

    // Workers
    async getAllWorkers() {
        return await this.request(API_CONFIG.ENDPOINTS.WORKERS);
    }

    async getWorkerById(id) {
        return await this.request(API_CONFIG.ENDPOINTS.WORKER_BY_ID(id));
    }

    async createWorker(workerData) {
        return await this.request(API_CONFIG.ENDPOINTS.WORKERS, {
            method: 'POST',
            body: JSON.stringify(workerData)
        });
    }

    async updateWorker(id, workerData) {
        return await this.request(API_CONFIG.ENDPOINTS.WORKER_BY_ID(id), {
            method: 'PUT',
            body: JSON.stringify(workerData)
        });
    }

    async deleteWorker(id) {
        return await this.request(API_CONFIG.ENDPOINTS.WORKER_BY_ID(id), {
            method: 'DELETE'
        });
    }

    async getAvailableWorkers() {
        return await this.request(API_CONFIG.ENDPOINTS.AVAILABLE_WORKERS);
    }

    // Comments
    async getCommentsByIssue(issueId) {
        return await this.request(API_CONFIG.ENDPOINTS.COMMENTS_BY_ISSUE(issueId));
    }

    async createComment(commentData, issueId) {
        return await this.request(`${API_CONFIG.ENDPOINTS.COMMENTS}?issueId=${issueId}`, {
            method: 'POST',
            body: JSON.stringify(commentData)
        });
    }

    async deleteComment(id) {
        return await this.request(`${API_CONFIG.ENDPOINTS.COMMENTS}/${id}`, {
            method: 'DELETE'
        });
    }

    logout() {
        this.setToken(null);
        sessionStorage.clear();
    }
}

// Create global API client instance
const apiClient = new APIClient();
