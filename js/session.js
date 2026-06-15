async function requireRole(expectedRole) {
    try {
        const response = await fetch('/BankingApp/api/auth');
        if (!response.ok) {
            window.location.replace('/BankingApp/login.html');
            return;
        }

        const user = await response.json();
        if (user.role !== expectedRole) {
            const dashboard = user.role === 'EMPLOYEE'
                ? 'employee-dashboard.html'
                : 'customer-dashboard.html';
            window.location.replace(`/BankingApp/${dashboard}`);
            return;
        }

        document.querySelectorAll('[data-user-name]')
            .forEach(element => element.textContent = user.fullName);
        document.querySelectorAll('[data-user-id]')
            .forEach(element => element.textContent = user.userId);
    } catch (error) {
        window.location.replace('/BankingApp/login.html');
    }
}
