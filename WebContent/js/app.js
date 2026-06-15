function showPage(pageId) {
    // Hide all pages
    const pages = document.querySelectorAll('.page');
    pages.forEach(page => page.classList.remove('active'));

    // Show selected page
    document.getElementById(pageId).classList.add('active');
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
    const password = document.getElementById('password')?.value || 'default123';

    if (!customerId || !customerName || !username) {
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
    const customerId = document.getElementById('accountCustomerId').value;
    const balance = document.getElementById('accountBalance').value;
    const accountType = document.getElementById('accountType').value;

    if (!accountId || !customerId || !balance) {
        showResult('accountResult', 'Please fill in all fields', false);
        return;
    }

    fetch('/BankingApp/api/account', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `action=create&accountId=${accountId}&customerId=${customerId}&balance=${balance}&accountType=${accountType}`
    })
    .then(response => response.json())
    .then(data => {
        showResult('accountResult', data.message, data.status === 'success');
        if (data.status === 'success') {
            document.getElementById('accountId').value = '';
            document.getElementById('accountCustomerId').value = '';
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
                showResult('accountResult', `Account ${data.accountId} - Balance: $${data.balance} - Status: ${data.status}`, true);
            } else {
                showResult('accountResult', data.message, false);
            }
        })
        .catch(error => showResult('accountResult', 'Error: ' + error, false));
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

// Utility function to display results
function showResult(elementId, message, isSuccess) {
    const resultElement = document.getElementById(elementId);
    resultElement.textContent = message;
    resultElement.classList.remove('success', 'error');
    resultElement.classList.add(isSuccess ? 'success' : 'error');
}

// Show dashboard on page load
document.addEventListener('DOMContentLoaded', () => {
    showPage('dashboard');
});
