// ========================================
// GEOREPORT SYSTEM - CONSOLIDATED APP.JS
// All JavaScript functionality in one file
// ========================================

// ========================================
// DATABASE MANAGER
// ========================================
class DatabaseManager {
    constructor() {
        this.dbName = 'GeoReportDB';
        this.initDatabase();
    }

    initDatabase() {
        if (!localStorage.getItem(this.dbName)) {
            const db = {
                issues: [],
                users: [],
                publicUsers: [],
                comments: [],
                attachments: [],
                workers: [],
                assignments: [],
                publicComplaints: [],
                settings: {
                    lastIssueId: 1005,
                    lastComplaintId: 2000,
                    lastPublicUserId: 1000,
                    autoRefresh: true,
                    refreshInterval: 30000
                }
            };
            this.saveDatabase(db);
            this.seedDefaultData();
        }
    }

    getDatabase() {
        const dbString = localStorage.getItem(this.dbName);
        return dbString ? JSON.parse(dbString) : null;
    }

    saveDatabase(db) {
        localStorage.setItem(this.dbName, JSON.stringify(db));
    }

    seedDefaultData() {
        const db = this.getDatabase();
        
        db.users = [
            {
                id: 1,
                username: 'admin',
                password: 'admin123',
                role: 'admin',
                name: 'Administrator',
                email: 'admin@georeport.com',
                createdAt: new Date().toISOString()
            },
            {
                id: 2,
                username: 'supervisor',
                password: 'super123',
                role: 'supervisor',
                name: 'Supervisor User',
                email: 'supervisor@georeport.com',
                createdAt: new Date().toISOString()
            },
            {
                id: 3,
                username: 'government',
                password: 'govt123',
                role: 'government',
                name: 'Government Official',
                email: 'govt@tn.gov.in',
                createdAt: new Date().toISOString()
            }
        ];

        db.issues = [
            {
                id: 'TN1001',
                type: 'Pothole',
                category: 'Road',
                description: 'Major pothole on Anna Salai, Chennai.',
                location: 'Chennai',
                address: 'Anna Salai, Chennai',
                lat: 13.0827,
                lng: 80.2707,
                status: 'Pending',
                priority: 'High',
                reportedBy: 'Citizen User',
                reportedAt: new Date(Date.now() - 2 * 60 * 60 * 1000).toISOString(),
                assignedTo: null,
                resolvedAt: null,
                estimatedResolution: null
            },
            {
                id: 'TN1002',
                type: 'Garbage Dump',
                category: 'Garbage',
                description: 'Overflowing garbage bin near Salem bus stand.',
                location: 'Salem',
                address: 'Bus Stand Road, Salem',
                lat: 11.1271,
                lng: 78.6569,
                status: 'In Progress',
                priority: 'Medium',
                reportedBy: 'Citizen User',
                reportedAt: new Date(Date.now() - 5 * 60 * 60 * 1000).toISOString(),
                assignedTo: 'Ramesh Kumar',
                resolvedAt: null,
                estimatedResolution: new Date(Date.now() + 24 * 60 * 60 * 1000).toISOString()
            },
            {
                id: 'TN1003',
                type: 'Streetlight Out',
                category: 'Streetlight',
                description: 'Streetlight not working on Rockfort Road, Trichy.',
                location: 'Trichy',
                address: 'Rockfort Road, Trichy',
                lat: 10.7905,
                lng: 78.7047,
                status: 'Resolved',
                priority: 'Low',
                reportedBy: 'Citizen User',
                reportedAt: new Date(Date.now() - 24 * 60 * 60 * 1000).toISOString(),
                assignedTo: 'Senthil Raj',
                resolvedAt: new Date(Date.now() - 2 * 60 * 60 * 1000).toISOString(),
                estimatedResolution: null
            },
            {
                id: 'TN1004',
                type: 'Water Leakage',
                category: 'Water',
                description: 'Water pipe leakage near Nagercoil railway station.',
                location: 'Nagercoil',
                address: 'Railway Station Road, Nagercoil',
                lat: 8.0883,
                lng: 77.5385,
                status: 'Pending',
                priority: 'High',
                reportedBy: 'Citizen User',
                reportedAt: new Date(Date.now() - 30 * 60 * 1000).toISOString(),
                assignedTo: null,
                resolvedAt: null,
                estimatedResolution: null
            },
            {
                id: 'TN1005',
                type: 'Road Damage',
                category: 'Road',
                description: 'Damaged road near Vellore Fort.',
                location: 'Vellore',
                address: 'Fort Road, Vellore',
                lat: 12.9716,
                lng: 79.1588,
                status: 'In Progress',
                priority: 'Medium',
                reportedBy: 'Citizen User',
                reportedAt: new Date(Date.now() - 60 * 60 * 1000).toISOString(),
                assignedTo: 'Murugan S',
                resolvedAt: null,
                estimatedResolution: new Date(Date.now() + 48 * 60 * 60 * 1000).toISOString()
            }
        ];

        db.workers = [
            {
                id: 1,
                name: 'Ramesh Kumar',
                phone: '+91 9876543210',
                email: 'ramesh@georeport.com',
                specialization: 'Garbage Management',
                zone: 'North Chennai',
                activeIssues: 1,
                resolvedIssues: 12,
                status: 'active'
            },
            {
                id: 2,
                name: 'Senthil Raj',
                phone: '+91 9876543211',
                email: 'senthil@georeport.com',
                specialization: 'Electrical Works',
                zone: 'Central Trichy',
                activeIssues: 0,
                resolvedIssues: 25,
                status: 'active'
            },
            {
                id: 3,
                name: 'Murugan S',
                phone: '+91 9876543212',
                email: 'murugan@georeport.com',
                specialization: 'Road Maintenance',
                zone: 'Vellore District',
                activeIssues: 1,
                resolvedIssues: 18,
                status: 'active'
            },
            {
                id: 4,
                name: 'Karthik P',
                phone: '+91 9876543213',
                email: 'karthik@georeport.com',
                specialization: 'Plumbing & Water',
                zone: 'South Tamil Nadu',
                activeIssues: 0,
                resolvedIssues: 15,
                status: 'active'
            }
        ];

        db.comments = [
            {
                id: 1,
                issueId: 'TN1002',
                userId: 1,
                userName: 'Administrator',
                comment: 'Assigned to Ramesh. Please resolve within 24 hours.',
                createdAt: new Date(Date.now() - 4 * 60 * 60 * 1000).toISOString()
            },
            {
                id: 2,
                issueId: 'TN1003',
                userId: 1,
                userName: 'Administrator',
                comment: 'Issue has been successfully resolved. Streetlight is now operational.',
                createdAt: new Date(Date.now() - 2 * 60 * 60 * 1000).toISOString()
            }
        ];

        this.saveDatabase(db);
    }

    // ========== ISSUE OPERATIONS ==========
    getAllIssues() {
        const db = this.getDatabase();
        return db.issues || [];
    }

    getIssueById(id) {
        const db = this.getDatabase();
        return db.issues.find(issue => issue.id === id);
    }

    createIssue(issueData) {
        const db = this.getDatabase();
        const newId = `TN${db.settings.lastIssueId + 1}`;
        
        const newIssue = {
            id: newId,
            ...issueData,
            reportedAt: new Date().toISOString(),
            assignedTo: null,
            resolvedAt: null
        };
        
        db.issues.push(newIssue);
        db.settings.lastIssueId += 1;
        this.saveDatabase(db);
        
        return newIssue;
    }

    updateIssue(id, updates) {
        const db = this.getDatabase();
        const issueIndex = db.issues.findIndex(issue => issue.id === id);
        
        if (issueIndex !== -1) {
            db.issues[issueIndex] = { ...db.issues[issueIndex], ...updates };
            
            if (updates.status === 'Resolved' && !db.issues[issueIndex].resolvedAt) {
                db.issues[issueIndex].resolvedAt = new Date().toISOString();
            }
            
            this.saveDatabase(db);
            return db.issues[issueIndex];
        }
        return null;
    }

    deleteIssue(id) {
        const db = this.getDatabase();
        db.issues = db.issues.filter(issue => issue.id !== id);
        this.saveDatabase(db);
        return true;
    }

    // ========== COMMENT OPERATIONS ==========
    getCommentsByIssueId(issueId) {
        const db = this.getDatabase();
        return db.comments.filter(comment => comment.issueId === issueId) || [];
    }

    addComment(commentData) {
        const db = this.getDatabase();
        const newId = db.comments.length > 0 ? Math.max(...db.comments.map(c => c.id)) + 1 : 1;
        
        const newComment = {
            id: newId,
            ...commentData,
            createdAt: new Date().toISOString()
        };
        
        db.comments.push(newComment);
        this.saveDatabase(db);
        
        return newComment;
    }

    // ========== WORKER OPERATIONS ==========
    getAllWorkers() {
        const db = this.getDatabase();
        return db.workers || [];
    }

    getWorkerById(id) {
        const db = this.getDatabase();
        return db.workers.find(worker => worker.id === id);
    }

    updateWorker(id, updates) {
        const db = this.getDatabase();
        const workerIndex = db.workers.findIndex(worker => worker.id === id);
        
        if (workerIndex !== -1) {
            db.workers[workerIndex] = { ...db.workers[workerIndex], ...updates };
            this.saveDatabase(db);
            return db.workers[workerIndex];
        }
        return null;
    }

    // ========== AUTHENTICATION ==========
    authenticateUser(username, password) {
        const db = this.getDatabase();
        return db.users.find(user => user.username === username && user.password === password);
    }

    // ========== STATISTICS ==========
    getStatistics() {
        const db = this.getDatabase();
        const issues = db.issues;
        
        return {
            total: issues.length,
            pending: issues.filter(i => i.status === 'Pending').length,
            inProgress: issues.filter(i => i.status === 'In Progress').length,
            resolved: issues.filter(i => i.status === 'Resolved').length,
            highPriority: issues.filter(i => i.priority === 'High').length,
            mediumPriority: issues.filter(i => i.priority === 'Medium').length,
            lowPriority: issues.filter(i => i.priority === 'Low').length,
            byCategory: {
                road: issues.filter(i => i.category === 'Road').length,
                garbage: issues.filter(i => i.category === 'Garbage').length,
                streetlight: issues.filter(i => i.category === 'Streetlight').length,
                water: issues.filter(i => i.category === 'Water').length
            }
        };
    }

    // ========== PUBLIC COMPLAINT OPERATIONS ==========
    getAllPublicComplaints() {
        const db = this.getDatabase();
        return db.publicComplaints || [];
    }

    getComplaintById(id) {
        const db = this.getDatabase();
        return db.publicComplaints.find(complaint => complaint.id === id);
    }

    createPublicComplaint(complaintData) {
        const db = this.getDatabase();
        const newId = `PC${db.settings.lastComplaintId + 1}`;
        
        const newComplaint = {
            id: newId,
            ...complaintData,
            status: 'Submitted',
            submittedAt: new Date().toISOString(),
            reviewedAt: null,
            reviewedBy: null,
            reviewNotes: null,
            convertedToIssueId: null
        };
        
        db.publicComplaints.push(newComplaint);
        db.settings.lastComplaintId += 1;
        this.saveDatabase(db);
        
        return newComplaint;
    }

    updateComplaintStatus(id, status, reviewedBy, reviewNotes) {
        const db = this.getDatabase();
        const complaintIndex = db.publicComplaints.findIndex(c => c.id === id);
        
        if (complaintIndex !== -1) {
            db.publicComplaints[complaintIndex].status = status;
            db.publicComplaints[complaintIndex].reviewedAt = new Date().toISOString();
            db.publicComplaints[complaintIndex].reviewedBy = reviewedBy;
            db.publicComplaints[complaintIndex].reviewNotes = reviewNotes || null;
            
            this.saveDatabase(db);
            return db.publicComplaints[complaintIndex];
        }
        return null;
    }

    convertComplaintToIssue(complaintId) {
        const db = this.getDatabase();
        const complaint = db.publicComplaints.find(c => c.id === complaintId);
        
        if (!complaint) return null;

        const newIssue = {
            id: `TN${db.settings.lastIssueId + 1}`,
            type: complaint.type,
            category: complaint.category,
            description: complaint.description,
            location: complaint.location,
            address: complaint.address,
            lat: complaint.lat,
            lng: complaint.lng,
            status: 'Pending',
            priority: complaint.priority,
            reportedBy: `${complaint.reportedBy} (Public)`,
            reportedAt: new Date().toISOString(),
            assignedTo: null,
            resolvedAt: null,
            estimatedResolution: null,
            sourceComplaintId: complaintId,
            complainantPhone: complaint.phone,
            complainantEmail: complaint.email
        };

        db.issues.push(newIssue);
        db.settings.lastIssueId += 1;

        const complaintIndex = db.publicComplaints.findIndex(c => c.id === complaintId);
        db.publicComplaints[complaintIndex].status = 'Converted';
        db.publicComplaints[complaintIndex].convertedToIssueId = newIssue.id;

        this.saveDatabase(db);
        return newIssue;
    }

    deleteComplaint(id) {
        const db = this.getDatabase();
        db.publicComplaints = db.publicComplaints.filter(c => c.id !== id);
        this.saveDatabase(db);
        return true;
    }

    getComplaintStatistics() {
        const db = this.getDatabase();
        const complaints = db.publicComplaints;
        
        return {
            total: complaints.length,
            submitted: complaints.filter(c => c.status === 'Submitted').length,
            underReview: complaints.filter(c => c.status === 'Under Review').length,
            approved: complaints.filter(c => c.status === 'Approved').length,
            rejected: complaints.filter(c => c.status === 'Rejected').length,
            converted: complaints.filter(c => c.status === 'Converted').length,
            byCategory: {
                road: complaints.filter(c => c.category === 'Road').length,
                garbage: complaints.filter(c => c.category === 'Garbage').length,
                streetlight: complaints.filter(c => c.category === 'Streetlight').length,
                water: complaints.filter(c => c.category === 'Water').length,
                other: complaints.filter(c => c.category === 'Other').length
            },
            byPriority: {
                high: complaints.filter(c => c.priority === 'High').length,
                medium: complaints.filter(c => c.priority === 'Medium').length,
                low: complaints.filter(c => c.priority === 'Low').length
            }
        };
    }

    // ========== PUBLIC USER OPERATIONS ==========
    registerPublicUser(userData) {
        const db = this.getDatabase();
        
        const existingUser = db.publicUsers.find(u => 
            u.email === userData.email || u.phone === userData.phone
        );
        
        if (existingUser) {
            return {
                success: false,
                message: 'User with this email or phone already exists'
            };
        }

        const newUser = {
            id: `PU${db.settings.lastPublicUserId + 1}`,
            name: userData.name,
            email: userData.email,
            phone: userData.phone,
            password: userData.password,
            address: userData.address || '',
            city: userData.city || '',
            createdAt: new Date().toISOString(),
            lastLogin: null,
            isActive: true,
            totalComplaints: 0,
            resolvedComplaints: 0
        };

        db.publicUsers.push(newUser);
        db.settings.lastPublicUserId += 1;
        this.saveDatabase(db);

        return {
            success: true,
            user: newUser,
            message: 'Registration successful'
        };
    }

    loginPublicUser(emailOrPhone, password) {
        const db = this.getDatabase();
        const user = db.publicUsers.find(u => 
            (u.email === emailOrPhone || u.phone === emailOrPhone) && 
            u.password === password && 
            u.isActive
        );

        if (user) {
            const userIndex = db.publicUsers.findIndex(u => u.id === user.id);
            db.publicUsers[userIndex].lastLogin = new Date().toISOString();
            this.saveDatabase(db);

            return {
                success: true,
                user: user,
                message: 'Login successful'
            };
        }

        return {
            success: false,
            message: 'Invalid credentials'
        };
    }

    getPublicUserById(id) {
        const db = this.getDatabase();
        return db.publicUsers.find(u => u.id === id);
    }

    updatePublicUser(id, updates) {
        const db = this.getDatabase();
        const userIndex = db.publicUsers.findIndex(u => u.id === id);
        
        if (userIndex !== -1) {
            if (updates.email || updates.phone) {
                const existing = db.publicUsers.find(u => 
                    u.id !== id && 
                    (u.email === updates.email || u.phone === updates.phone)
                );
                if (existing) {
                    return {
                        success: false,
                        message: 'Email or phone already in use'
                    };
                }
            }

            db.publicUsers[userIndex] = { 
                ...db.publicUsers[userIndex], 
                ...updates,
                id: id
            };
            this.saveDatabase(db);
            return {
                success: true,
                user: db.publicUsers[userIndex],
                message: 'Profile updated successfully'
            };
        }
        return {
            success: false,
            message: 'User not found'
        };
    }

    getComplaintsByUserId(userId) {
        const db = this.getDatabase();
        return db.publicComplaints.filter(c => c.userId === userId);
    }

    createPublicComplaintWithUser(complaintData, userId) {
        const db = this.getDatabase();
        const newId = `PC${db.settings.lastComplaintId + 1}`;
        
        const newComplaint = {
            id: newId,
            userId: userId,
            ...complaintData,
            status: 'Submitted',
            submittedAt: new Date().toISOString(),
            reviewedAt: null,
            reviewedBy: null,
            reviewNotes: null,
            convertedToIssueId: null
        };
        
        db.publicComplaints.push(newComplaint);
        db.settings.lastComplaintId += 1;

        const userIndex = db.publicUsers.findIndex(u => u.id === userId);
        if (userIndex !== -1) {
            db.publicUsers[userIndex].totalComplaints += 1;
        }

        this.saveDatabase(db);
        return newComplaint;
    }

    getPublicUserStatistics(userId) {
        const db = this.getDatabase();
        const complaints = db.publicComplaints.filter(c => c.userId === userId);
        
        return {
            total: complaints.length,
            submitted: complaints.filter(c => c.status === 'Submitted').length,
            underReview: complaints.filter(c => c.status === 'Under Review').length,
            approved: complaints.filter(c => c.status === 'Approved').length,
            rejected: complaints.filter(c => c.status === 'Rejected').length,
            converted: complaints.filter(c => c.status === 'Converted').length,
            resolved: complaints.filter(c => c.status === 'Converted' && c.convertedToIssueId).length
        };
    }

    // ========== UTILITY ==========
    exportToJSON() {
        return this.getDatabase();
    }

    importFromJSON(data) {
        this.saveDatabase(data);
    }

    clearDatabase() {
        localStorage.removeItem(this.dbName);
        this.initDatabase();
    }
}

// ========================================
// AUTHENTICATION MANAGER
// ========================================
class AuthManager {
    constructor() {
        this.currentUser = null;
        this.checkSession();
    }

    checkSession() {
        const sessionUser = sessionStorage.getItem('currentUser');
        if (sessionUser) {
            this.currentUser = JSON.parse(sessionUser);
            return true;
        }
        return false;
    }

    login(username, password) {
        const user = DB.authenticateUser(username, password);
        
        if (user) {
            const { password: pwd, ...userWithoutPassword } = user;
            this.currentUser = userWithoutPassword;
            sessionStorage.setItem('currentUser', JSON.stringify(userWithoutPassword));
            return { success: true, user: userWithoutPassword };
        }
        
        return { success: false, message: 'Invalid username or password' };
    }

    logout() {
        this.currentUser = null;
        sessionStorage.removeItem('currentUser');
        window.location.href = 'login.html';
    }

    getCurrentUser() {
        return this.currentUser;
    }

    hasRole(role) {
        return this.currentUser && this.currentUser.role === role;
    }

    requireAuth() {
        if (!this.checkSession()) {
            window.location.href = 'login.html';
            return false;
        }
        return true;
    }

    updateProfile(updates) {
        if (this.currentUser) {
            this.currentUser = { ...this.currentUser, ...updates };
            sessionStorage.setItem('currentUser', JSON.stringify(this.currentUser));
            return true;
        }
        return false;
    }
}

// ========================================
// ISSUE MANAGER
// ========================================
class IssueManager {
    constructor() {
        this.issues = [];
        this.loadIssues();
    }

    loadIssues() {
        this.issues = DB.getAllIssues();
        return this.issues;
    }

    getAllIssues() {
        return this.issues;
    }

    getIssueById(id) {
        return this.issues.find(issue => issue.id === id);
    }

    createIssue(issueData) {
        const newIssue = DB.createIssue(issueData);
        this.loadIssues();
        return newIssue;
    }

    updateIssue(id, updates) {
        const updatedIssue = DB.updateIssue(id, updates);
        this.loadIssues();
        return updatedIssue;
    }

    deleteIssue(id) {
        const result = DB.deleteIssue(id);
        this.loadIssues();
        return result;
    }

    assignIssue(issueId, workerName, estimatedDays = 2) {
        const estimatedResolution = new Date();
        estimatedResolution.setDate(estimatedResolution.getDate() + estimatedDays);

        return this.updateIssue(issueId, {
            assignedTo: workerName,
            status: 'In Progress',
            estimatedResolution: estimatedResolution.toISOString()
        });
    }

    resolveIssue(issueId, resolution = '') {
        return this.updateIssue(issueId, {
            status: 'Resolved',
            resolvedAt: new Date().toISOString(),
            resolution: resolution
        });
    }

    changePriority(issueId, priority) {
        return this.updateIssue(issueId, { priority });
    }

    filterIssues(filters) {
        let filtered = [...this.issues];

        if (filters.status && filters.status !== 'All') {
            filtered = filtered.filter(i => i.status === filters.status);
        }

        if (filters.priority && filters.priority !== 'All') {
            filtered = filtered.filter(i => i.priority === filters.priority);
        }

        if (filters.category && filters.category !== 'All') {
            filtered = filtered.filter(i => i.category === filters.category);
        }

        if (filters.search) {
            const searchLower = filters.search.toLowerCase();
            filtered = filtered.filter(i =>
                i.id.toLowerCase().includes(searchLower) ||
                i.description.toLowerCase().includes(searchLower) ||
                i.location.toLowerCase().includes(searchLower) ||
                i.type.toLowerCase().includes(searchLower)
            );
        }

        return filtered;
    }

    getStatistics() {
        return DB.getStatistics();
    }

    getRecentIssues(count = 5) {
        return [...this.issues]
            .sort((a, b) => new Date(b.reportedAt) - new Date(a.reportedAt))
            .slice(0, count);
    }

    getHighPriorityIssues() {
        return this.issues.filter(i => i.priority === 'High' && i.status !== 'Resolved');
    }

    getOverdueIssues() {
        const now = new Date();
        return this.issues.filter(i => {
            if (i.status === 'Resolved' || !i.estimatedResolution) return false;
            return new Date(i.estimatedResolution) < now;
        });
    }

    exportToCSV(issues = null) {
        const issuesToExport = issues || this.issues;
        let csv = 'Issue ID,Type,Category,Description,Location,Status,Priority,Reported By,Reported At,Assigned To,Resolved At\n';

        issuesToExport.forEach(issue => {
            const row = [
                issue.id,
                issue.type,
                issue.category,
                `"${issue.description.replace(/"/g, '""')}"`,
                issue.location,
                issue.status,
                issue.priority,
                issue.reportedBy,
                new Date(issue.reportedAt).toLocaleString(),
                issue.assignedTo || 'Unassigned',
                issue.resolvedAt ? new Date(issue.resolvedAt).toLocaleString() : 'Not Resolved'
            ];
            csv += row.join(',') + '\n';
        });

        return csv;
    }

    downloadCSV(filename = 'issues.csv', issues = null) {
        const csv = this.exportToCSV(issues);
        const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
        const link = document.createElement('a');
        const url = URL.createObjectURL(blob);

        link.setAttribute('href', url);
        link.setAttribute('download', filename);
        link.style.visibility = 'hidden';

        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }
}

// ========================================
// COMMENT MANAGER
// ========================================
class CommentManager {
    constructor() {
        this.comments = [];
    }

    getCommentsByIssueId(issueId) {
        return DB.getCommentsByIssueId(issueId);
    }

    addComment(issueId, comment) {
        const currentUser = Auth.getCurrentUser();
        if (!currentUser) return null;

        const commentData = {
            issueId: issueId,
            userId: currentUser.id,
            userName: currentUser.name,
            comment: comment
        };

        return DB.addComment(commentData);
    }

    formatComment(comment) {
        const date = new Date(comment.createdAt);
        const timeAgo = this.getTimeAgo(date);

        return {
            ...comment,
            formattedDate: date.toLocaleString(),
            timeAgo: timeAgo
        };
    }

    getTimeAgo(date) {
        const seconds = Math.floor((new Date() - date) / 1000);
        
        let interval = Math.floor(seconds / 31536000);
        if (interval > 1) return interval + ' years ago';
        
        interval = Math.floor(seconds / 2592000);
        if (interval > 1) return interval + ' months ago';
        
        interval = Math.floor(seconds / 86400);
        if (interval > 1) return interval + ' days ago';
        
        interval = Math.floor(seconds / 3600);
        if (interval > 1) return interval + ' hours ago';
        
        interval = Math.floor(seconds / 60);
        if (interval > 1) return interval + ' minutes ago';
        
        return 'Just now';
    }
}

// ========================================
// GLOBAL INSTANCES
// ========================================
const DB = new DatabaseManager();
const Auth = new AuthManager();
const IssueService = new IssueManager();
const CommentService = new CommentManager();
