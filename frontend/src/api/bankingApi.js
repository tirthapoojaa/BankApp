import axios from 'axios';

const formHeaders = {
  'Content-Type': 'application/x-www-form-urlencoded'
};

function toFormBody(data) {
  const body = new URLSearchParams();

  Object.entries(data).forEach(([key, value]) => {
    if (value !== undefined && value !== null) {
      body.append(key, value);
    }
  });

  return body;
}

function responseUrl(response) {
  return response?.request?.responseURL || '';
}

function queryParam(url, key) {
  if (!url) {
    return null;
  }

  return new URL(url, window.location.origin).searchParams.get(key);
}

export async function submitLogin(formData) {
  const backendRole = formData.role === 'CUSTOMER' ? 'CUSTOMER' : 'EMPLOYEE';
  const response = await axios.post(
    '/BankingApp/login',
    toFormBody({
      userId: formData.userId,
      password: formData.password,
      role: backendRole
    }),
    {
      headers: formHeaders,
      validateStatus: () => true
    }
  );

  if (response.status === 429) {
    return {
      success: false,
      message: response.data?.message || 'Too many login attempts.'
    };
  }

  const redirectUrl = responseUrl(response);
  if (redirectUrl.includes('employee-dashboard.html')) {
    return {
      success: true,
      dashboardPath: '/BankingApp/employee-dashboard.html'
    };
  }

  if (redirectUrl.includes('customer-dashboard.html')) {
    return {
      success: true,
      dashboardPath: '/BankingApp/customer-dashboard.html'
    };
  }

  return {
    success: false,
    message: 'Invalid user ID, password, or role.'
  };
}

export async function registerCustomer(formData) {
  const response = await axios.post(
    '/BankingApp/register',
    toFormBody(formData),
    {
      headers: formHeaders,
      validateStatus: () => true
    }
  );

  const redirectUrl = responseUrl(response);
  if (queryParam(redirectUrl, 'registered') === 'true') {
    return {
      success: true,
      message: 'Customer registration completed. You can now sign in.'
    };
  }

  return {
    success: false,
    message: queryParam(redirectUrl, 'registrationError')
      || 'Customer registration failed.'
  };
}

export async function registerEmployee(formData) {
  const response = await axios.post(
    '/BankingApp/register-employee',
    toFormBody({
      action: 'register',
      employeeId: formData.employeeId,
      name: formData.fullName,
      userId: formData.userId,
      password: formData.password,
      confirmPassword: formData.confirmPassword,
      employeeRole: formData.employeeRole,
      salary: formData.salary,
      branchId: formData.branchId
    }),
    {
      headers: formHeaders,
      validateStatus: () => true
    }
  );

  const redirectUrl = responseUrl(response);
  if (queryParam(redirectUrl, 'employeeRegistered') === 'true') {
    return {
      success: true,
      message: 'Employee registration completed. You can now sign in.'
    };
  }

  return {
    success: false,
    message: queryParam(redirectUrl, 'employeeRegistrationError')
      || 'Employee registration failed.'
  };
}

export async function fetchBranches() {
  const response = await axios.get('/BankingApp/branches?action=list');

  if (response.data?.status !== 'success') {
    throw new Error(response.data?.message || 'Unable to load branches.');
  }

  return response.data.branches || [];
}

export async function logoutUser() {
  const response = await axios.post('/BankingApp/logout', {}, {
    withCredentials: true,
    validateStatus: () => true
  });

  if (response.status >= 200 && response.status < 300
      && response.data?.success) {
    return response.data;
  }

  throw new Error(response.data?.message || 'Logout failed.');
}
