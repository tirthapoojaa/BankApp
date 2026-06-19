export function required(value, message) {
  return String(value || '').trim() ? '' : message;
}

export function validatePasswordMatch(password, confirmPassword) {
  if (!confirmPassword) {
    return 'Confirm password is required.';
  }

  return password === confirmPassword ? '' : 'Passwords do not match.';
}

export function validateMinimumPassword(password) {
  if (!password) {
    return 'Password is required.';
  }

  return password.length >= 8
    ? ''
    : 'Password must contain at least 8 characters.';
}
