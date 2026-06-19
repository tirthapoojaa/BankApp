let employeePermissions = new Set();

const employeePages = [
    {
        page: 'dashboard',
        label: 'Dashboard',
        title: 'Dashboard',
        description: 'View your permitted employee actions.'
    },
    {
        page: 'assignedBranch',
        permission: 'assignedBranch',
        label: 'My Branch',
        title: 'View Assigned Branch',
        description: 'View your assigned branch and bank.',
        onOpen: 'loadAssignedBranch'
    },
    {
        page: 'branchAccounts',
        permission: 'branchAccounts',
        label: 'Branch Accounts',
        title: 'View Branch Accounts',
        description: 'Search accounts and review balances in your branch.',
        onOpen: 'loadBranchAccounts'
    },
    {
        page: 'bank',
        permission: 'viewBank',
        label: 'Bank',
        title: 'Bank Management',
        description: 'Create or view bank records.'
    },
    {
        page: 'branch',
        permission: 'viewBranch',
        label: 'Branch',
        title: 'Branch Management',
        description: 'Create or view branch records.'
    },
    {
        page: 'customer',
        permission: 'viewCustomer',
        label: 'Customer',
        title: 'Customer Management',
        description: 'Create or view customer records.'
    },
    {
        page: 'employee',
        permission: 'viewEmployee',
        label: 'Employee',
        title: 'Employee Management',
        description: 'Create or view employee records.'
    },
    {
        page: 'account',
        permission: 'viewAccount',
        label: 'Account',
        title: 'Account Management',
        description: 'Create or view account records.'
    },
    {
        page: 'transaction',
        permission: 'transactionHistory',
        label: 'Transactions',
        title: 'Transactions',
        description: 'Deposit, withdraw, or view transaction history.'
    }
];

function configureEmployeeDashboard(user) {
    const permissions = Array.isArray(user.permissions) && user.permissions.length > 0
        ? user.permissions
        : employeePages
            .map(page => page.permission)
            .filter(permission => permission);
    employeePermissions = new Set(permissions);
    renderEmployeeNavigation();
    renderEmployeeDashboardCards();
    removeUnauthorizedElements();
    showPage('dashboard');
}

function hasEmployeePermission(permission) {
    return !permission || employeePermissions.has(permission);
}

function openEmployeePage(pageId, loaderName) {
    showPage(pageId);
    if (loaderName && typeof window[loaderName] === 'function') {
        window[loaderName]();
    }
}

function renderEmployeeNavigation() {
    const nav = document.getElementById('employeeNav');
    if (!nav) {
        return;
    }

    const logout = nav.querySelector('[data-logout-link]');
    nav.innerHTML = '';

    employeePages
        .filter(page => hasEmployeePermission(page.permission))
        .forEach(page => {
            const link = document.createElement('a');
            link.href = '#';
            link.textContent = page.label;
            link.addEventListener('click', event => {
                event.preventDefault();
                openEmployeePage(page.page, page.onOpen);
            });
            nav.appendChild(link);
        });

    if (logout) {
        nav.appendChild(logout);
    }
}

function setupLogoutLinks() {
    document.querySelectorAll('[data-logout-link]').forEach(link => {
        link.addEventListener('click', event => {
            event.preventDefault();
            fetch('/BankingApp/logout', {
                method: 'POST',
                credentials: 'include'
            }).finally(() => {
                localStorage.clear();
                sessionStorage.clear();
                window.location.assign('/login');
            });
        });
    });
}

function renderEmployeeDashboardCards() {
    const container = document.getElementById('employeeDashboardCards');
    if (!container) {
        return;
    }

    container.innerHTML = '';
    employeePages
        .filter(page => page.page !== 'dashboard')
        .filter(page => hasEmployeePermission(page.permission))
        .forEach(page => {
            const card = document.createElement('div');
            card.className = 'card';

            const title = document.createElement('h3');
            title.textContent = page.title;
            const description = document.createElement('p');
            description.textContent = page.description;
            const button = document.createElement('button');
            button.type = 'button';
            button.textContent = 'Go';
            button.addEventListener('click', () =>
                openEmployeePage(page.page, page.onOpen));

            card.appendChild(title);
            card.appendChild(description);
            card.appendChild(button);
            container.appendChild(card);
        });
}

function removeUnauthorizedElements() {
    document.querySelectorAll('[data-permission]').forEach(element => {
        if (!hasEmployeePermission(element.dataset.permission)) {
            element.remove();
        }
    });
}

function showPage(pageId) {
    const selectedPage = document.getElementById(pageId);
    if (!selectedPage) {
        return;
    }

    const requiredPermission = selectedPage.dataset.pagePermission;
    if (!hasEmployeePermission(requiredPermission)) {
        showPage('dashboard');
        return;
    }

    // Hide all pages
    const pages = document.querySelectorAll('.page');
    pages.forEach(page => page.classList.remove('active'));

    // Show selected page
    selectedPage.classList.add('active');
}

// Bank Operations
function createBank() {
    const bankId = document.getElementById('bankId').value;
    const bankName = document.getElementById('bankName').value;

    if (!bankId || !bankName) {
        showResult('bankResult', 'Please fill in all fields', false);
        return;
    }

    fetch('/BankingApp/api/bank', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `action=create&bankId=${bankId}&bankName=${bankName}`
    })
    .then(response => response.json())
    .then(data => {
        showResult('bankResult', data.message, data.status === 'success');
        if (data.status === 'success') {
            document.getElementById('bankId').value = '';
            document.getElementById('bankName').value = '';
        }
    })
    .catch(error => showResult('bankResult', 'Error: ' + error, false));
}

function viewBank() {
    const bankId = document.getElementById('viewBankId').value;

    if (!bankId) {
        showResult('bankResult', 'Please enter Bank ID', false);
        return;
    }

    fetch(`/BankingApp/api/bank?action=view&bankId=${bankId}`)
        .then(response => response.json())
        .then(data => {
            if (data.status === 'success') {
                showResult('bankResult', `Bank: ${data.bankName} (ID: ${data.bankId})`, true);
            } else {
                showResult('bankResult', data.message, false);
            }
        })
        .catch(error => showResult('bankResult', 'Error: ' + error, false));
}

// Branch Operations
function createBranch() {
    const branchId = document.getElementById('branchId').value;
    const branchName = document.getElementById('branchName').value;
    const bankId = document.getElementById('branchBankId').value;

    if (!branchId || !branchName || !bankId) {
        showResult('branchResult', 'Please fill in all fields', false);
        return;
    }

    const body = new URLSearchParams({
        action: 'create',
        branchId,
        branchName,
        bankId
    });

    fetch('/BankingApp/api/branch', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body
    })
    .then(response => response.json())
    .then(data => {
        showResult('branchResult', data.message, data.status === 'success');
        if (data.status === 'success') {
            document.getElementById('branchId').value = '';
            document.getElementById('branchName').value = '';
            document.getElementById('branchBankId').value = '';
        }
    })
    .catch(error => showResult('branchResult', 'Error: ' + error, false));
}

function viewBranch() {
    const branchId = document.getElementById('viewBranchId').value;

    if (!branchId) {
        showResult('branchResult', 'Please enter Branch ID', false);
        return;
    }

    fetch(`/BankingApp/api/branch?action=view&branchId=${branchId}`)
        .then(response => response.json())
        .then(data => {
            if (data.status === 'success') {
                showResult(
                    'branchResult',
                    `Branch: ${data.branchName} (ID: ${data.branchId}) - Bank: ${data.bankName} (ID: ${data.bankId})`,
                    true
                );
            } else {
                showResult('branchResult', data.message, false);
            }
        })
        .catch(error => showResult('branchResult', 'Error: ' + error, false));
}

// Customer Operations
function createCustomer() {
    const customerId = document.getElementById('customerId').value;
    const customerName = document.getElementById('customerName').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    if (!customerId || !customerName || !username || !password) {
        showResult('customerResult', 'Please fill in all fields', false);
        return;
    }

    fetch('/BankingApp/api/customer', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `action=create&customerId=${customerId}&name=${customerName}&username=${username}&password=${password}`
    })
    .then(response => response.json())
    .then(data => {
        showResult('customerResult', data.message, data.status === 'success');
        if (data.status === 'success') {
            document.getElementById('customerId').value = '';
            document.getElementById('customerName').value = '';
            document.getElementById('username').value = '';
            document.getElementById('password').value = '';
        }
    })
    .catch(error => showResult('customerResult', 'Error: ' + error, false));
}

// Employee Operations
function createEmployee() {
    const values = {
        action: 'create',
        employeeId: document.getElementById('employeeId').value,
        name: document.getElementById('employeeName').value,
        userId: document.getElementById('employeeUserId').value,
        password: document.getElementById('employeePassword').value,
        branchId: document.getElementById('employeeBranchId').value,
        employeeRole: document.getElementById('employeeRole').value,
        salary: document.getElementById('employeeSalary').value
    };

    if (!values.employeeId || !values.name || !values.userId
            || !values.password || !values.branchId || !values.salary) {
        showResult('employeeResult', 'Please fill in all required fields', false);
        return;
    }

    fetch('/BankingApp/api/employee', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams(values)
    })
    .then(response => response.json())
    .then(data => {
        showResult('employeeResult', data.message, data.status === 'success');
        if (data.status === 'success') {
            ['employeeId', 'employeeName', 'employeeUserId',
                'employeePassword', 'employeeBranchId', 'employeeSalary']
                .forEach(id => document.getElementById(id).value = '');
        }
    })
    .catch(error => showResult('employeeResult', 'Error: ' + error, false));
}

function viewEmployee() {
    const employeeId = document.getElementById('viewEmployeeId').value;

    if (!employeeId) {
        showResult('employeeResult', 'Please enter Employee ID', false);
        return;
    }

    fetch(`/BankingApp/api/employee?employeeId=${employeeId}`)
        .then(response => response.json())
        .then(data => {
            if (data.status === 'success') {
                showResult(
                    'employeeResult',
                    `${data.name} (${data.userId}) - ${data.employeeRole}`,
                    true
                );
            } else {
                showResult('employeeResult', data.message, false);
            }
        })
        .catch(error => showResult('employeeResult', 'Error: ' + error, false));
}

function viewCustomer() {
    const customerId = document.getElementById('viewCustomerId').value;

    if (!customerId) {
        showResult('customerResult', 'Please enter Customer ID', false);
        return;
    }

    fetch(`/BankingApp/api/customer?action=view&customerId=${customerId}`)
        .then(response => response.json())
        .then(data => {
            if (data.status === 'success') {
                showResult('customerResult', `Customer: ${data.name} (@${data.username})`, true);
            } else {
                showResult('customerResult', data.message, false);
            }
        })
        .catch(error => showResult('customerResult', 'Error: ' + error, false));
}

// Account Operations
function createAccount() {
    const accountId = document.getElementById('accountId').value;
    const customerId = document.getElementById('accountCustomerId')?.value;
    const branchId = document.getElementById('accountBranchId')?.value;
    const balance = document.getElementById('accountBalance').value;
    const accountType = document.getElementById('accountType').value;

    if (!accountId || balance === '' || (!customerId && !branchId)) {
        showResult('accountResult', 'Please fill in all fields', false);
        return;
    }

    const body = new URLSearchParams({
        action: 'create',
        accountId,
        balance,
        accountType
    });
    if (customerId) {
        body.set('customerId', customerId);
    }
    if (branchId) {
        body.set('branchId', branchId);
    }

    fetch('/BankingApp/api/account', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body
    })
    .then(response => response.json())
    .then(data => {
        showResult('accountResult', data.message, data.status === 'success');
        if (data.status === 'success') {
            document.getElementById('accountId').value = '';
            if (document.getElementById('accountCustomerId')) {
                document.getElementById('accountCustomerId').value = '';
            }
            document.getElementById('accountBalance').value = '';
        }
    })
    .catch(error => showResult('accountResult', 'Error: ' + error, false));
}

function viewAccount() {
    const accountId = document.getElementById('viewAccountId').value;

    if (!accountId) {
        showResult('accountResult', 'Please enter Account ID', false);
        return;
    }

    fetch(`/BankingApp/api/account?action=view&accountId=${accountId}`)
        .then(response => response.json())
        .then(data => {
            if (data.status === 'success') {
                const account = data.account;
                showResult(
                    'accountResult',
                    `Account ${account.accountId} - ${account.accountType} - Balance: $${account.balance} - Status: ${account.accountStatus}`,
                    true
                );
            } else {
                showResult('accountResult', data.message, false);
            }
        })
        .catch(error => showResult('accountResult', 'Error: ' + error, false));
}

function loadMyAccounts() {
    fetch('/BankingApp/api/account?action=myAccounts')
        .then(response => response.json())
        .then(data => renderAccounts(
            'myAccountsResult',
            data.accounts || [],
            data.message
        ))
        .catch(error => renderMessage('myAccountsResult', 'Error: ' + error, false));
}

function loadAssignedBranch() {
    fetch('/BankingApp/api/employee?action=assignedBranch')
        .then(response => response.json())
        .then(data => {
            const element = document.getElementById('assignedBranchResult');
            if (data.status !== 'success') {
                element.textContent = data.message;
                return;
            }
            element.innerHTML = `
                <p><strong>Branch ID:</strong> ${data.branchId}</p>
                <p><strong>Branch Name:</strong> ${data.branchName}</p>
                <p><strong>Bank:</strong> ${data.bankName} (${data.bankId})</p>
                <p><strong>Designation:</strong> ${data.designation}</p>
            `;
        })
        .catch(error => renderMessage(
            'assignedBranchResult', 'Error: ' + error, false));
}

function loadBranchAccounts() {
    fetch('/BankingApp/api/account?action=branchAccounts')
        .then(response => response.json())
        .then(data => renderAccounts(
            'branchAccountsResult',
            data.accounts || [],
            data.message
        ))
        .catch(error => renderMessage(
            'branchAccountsResult', 'Error: ' + error, false));
}

function searchBranchAccount() {
    const accountId = document.getElementById('branchAccountSearchId').value;
    if (!accountId) {
        renderMessage('branchAccountsResult', 'Enter an Account ID', false);
        return;
    }

    fetch(`/BankingApp/api/account?action=search&accountId=${accountId}`)
        .then(response => response.json())
        .then(data => renderAccounts(
            'branchAccountsResult',
            data.account ? [data.account] : [],
            data.message
        ))
        .catch(error => renderMessage(
            'branchAccountsResult', 'Error: ' + error, false));
}

// Transaction Operations
function deposit() {
    const accountId = document.getElementById('depositAccountId').value;
    const amount = document.getElementById('depositAmount').value;

    if (!accountId || !amount) {
        showResult('transactionResult', 'Please fill in all fields', false);
        return;
    }

    fetch('/BankingApp/api/transaction', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `action=deposit&accountId=${accountId}&amount=${amount}`
    })
    .then(response => response.json())
    .then(data => {
        showResult('transactionResult', data.message + ` New Balance: $${data.newBalance}`, data.status === 'success');
        if (data.status === 'success') {
            document.getElementById('depositAccountId').value = '';
            document.getElementById('depositAmount').value = '';
        }
    })
    .catch(error => showResult('transactionResult', 'Error: ' + error, false));
}

function withdraw() {
    const accountId = document.getElementById('withdrawAccountId').value;
    const amount = document.getElementById('withdrawAmount').value;

    if (!accountId || !amount) {
        showResult('transactionResult', 'Please fill in all fields', false);
        return;
    }

    fetch('/BankingApp/api/transaction', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `action=withdraw&accountId=${accountId}&amount=${amount}`
    })
    .then(response => response.json())
    .then(data => {
        showResult('transactionResult', data.message + ` New Balance: $${data.newBalance}`, data.status === 'success');
        if (data.status === 'success') {
            document.getElementById('withdrawAccountId').value = '';
            document.getElementById('withdrawAmount').value = '';
        }
    })
    .catch(error => showResult('transactionResult', 'Error: ' + error, false));
}

function viewTransactions() {
    const accountId = document.getElementById('historyAccountId').value;

    if (!accountId) {
        showResult('transactionResult', 'Please enter Account ID', false);
        return;
    }

    fetch(`/BankingApp/api/transaction?action=view&accountId=${accountId}`)
        .then(response => response.json())
        .then(data => {
            if (data.status !== 'success') {
                showResult('transactionResult', data.message, false);
                return;
            }

            const transactions = data.transactions || [];
            if (transactions.length === 0) {
                showResult('transactionResult', 'No transactions found', true);
                return;
            }

            const lines = transactions.map(transaction =>
                `#${transaction.transactionId} ${transaction.type}: $${transaction.amount} (${transaction.transactionStatus})`
            );
            showResult('transactionResult', lines.join('\n'), true);
        })
        .catch(error => showResult('transactionResult', 'Error: ' + error, false));
}

function renderAccounts(elementId, accounts, errorMessage) {
    const element = document.getElementById(elementId);
    if (!element) {
        return;
    }
    if (errorMessage) {
        renderMessage(elementId, errorMessage, false);
        return;
    }
    if (accounts.length === 0) {
        renderMessage(elementId, 'No accounts found', true);
        return;
    }

    const rows = accounts.map(account => `
        <tr>
            <td>${account.accountId}</td>
            <td>${account.customerName}</td>
            <td>${account.branchName}</td>
            <td>${account.accountType}</td>
            <td>$${Number(account.balance).toFixed(2)}</td>
            <td>${account.accountStatus}</td>
        </tr>
    `).join('');

    element.innerHTML = `
        <table class="data-table">
            <thead>
                <tr>
                    <th>Account</th>
                    <th>Customer</th>
                    <th>Branch</th>
                    <th>Type</th>
                    <th>Balance</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>${rows}</tbody>
        </table>
    `;
}

function renderMessage(elementId, message, isSuccess) {
    const element = document.getElementById(elementId);
    element.textContent = message;
    element.classList.toggle('success', isSuccess);
    element.classList.toggle('error', !isSuccess);
}
// Utility function to display results
function showResult(elementId, message, isSuccess) {
    const resultElement = document.getElementById(elementId);
    resultElement.textContent = message;
    resultElement.classList.remove('success', 'error');
    resultElement.classList.add(isSuccess ? 'success' : 'error');
}

// Show dashboard on page load
document.addEventListener('DOMContentLoaded', () => {
    setupLogoutLinks();
    showPage('dashboard');
});
