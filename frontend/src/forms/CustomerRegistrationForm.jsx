import { useState } from 'react';
import { registerCustomer } from '../api/bankingApi.js';
import FormButton from '../components/FormButton.jsx';
import Input from '../components/Input.jsx';
import PasswordInput from '../components/PasswordInput.jsx';
import {
  required,
  validateMinimumPassword,
  validatePasswordMatch
} from './formValidation.js';

const initialFormData = {
  customerId: '',
  fullName: '',
  userId: '',
  password: '',
  confirmPassword: ''
};

export default function CustomerRegistrationForm({ onSuccess }) {
  const [formData, setFormData] = useState(initialFormData);
  const [errors, setErrors] = useState({});
  const [formMessage, setFormMessage] = useState('');
  const [visiblePasswordField, setVisiblePasswordField] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const updateField = (fieldName) => (event) => {
    setFormData((currentData) => ({
      ...currentData,
      [fieldName]: event.target.value
    }));
    setErrors((currentErrors) => ({
      ...currentErrors,
      [fieldName]: ''
    }));
    setFormMessage('');
  };

  const validate = () => {
    const nextErrors = {
      customerId: required(formData.customerId, 'Customer ID is required.'),
      fullName: required(formData.fullName, 'Full name is required.'),
      userId: required(formData.userId, 'User ID is required.'),
      password: validateMinimumPassword(formData.password),
      confirmPassword: validatePasswordMatch(
        formData.password,
        formData.confirmPassword
      )
    };

    Object.keys(nextErrors).forEach((key) => {
      if (!nextErrors[key]) {
        delete nextErrors[key];
      }
    });

    setErrors(nextErrors);
    return Object.keys(nextErrors).length === 0;
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (!validate()) {
      return;
    }

    setIsSubmitting(true);
    setFormMessage('');

    try {
      const result = await registerCustomer(formData);
      if (result.success) {
        setFormData(initialFormData);
        onSuccess(result.message);
        return;
      }

      setFormMessage(result.message);
    } catch (error) {
      setFormMessage('Customer registration failed. Please try again.');
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <form className="space-y-5" onSubmit={handleSubmit}>
      {formMessage && (
        <div className="rounded-2xl border border-red-200 bg-red-50 px-4 py-3 text-sm font-medium text-[#DC2626]">
          {formMessage}
        </div>
      )}

      <div className="grid gap-5 md:grid-cols-2">
        <Input
          id="customerId"
          label="Customer ID"
          type="number"
          min="1"
          value={formData.customerId}
          onChange={updateField('customerId')}
          placeholder="Enter customer ID"
          error={errors.customerId}
        />

        <Input
          id="customerFullName"
          label="Full Name"
          value={formData.fullName}
          onChange={updateField('fullName')}
          placeholder="Enter full name"
          autoComplete="name"
          error={errors.fullName}
        />
      </div>

      <Input
        id="customerUserId"
        label="User ID"
        value={formData.userId}
        onChange={updateField('userId')}
        placeholder="Create user ID"
        autoComplete="username"
        error={errors.userId}
      />

      <div className="grid gap-5 md:grid-cols-2">
        <PasswordInput
          id="customerPassword"
          label="Password"
          value={formData.password}
          onChange={updateField('password')}
          isVisible={visiblePasswordField === 'password'}
          onToggleVisibility={() =>
            setVisiblePasswordField((currentValue) =>
              currentValue === 'password' ? '' : 'password')}
          autoComplete="new-password"
          error={errors.password}
        />

        <PasswordInput
          id="customerConfirmPassword"
          label="Confirm Password"
          value={formData.confirmPassword}
          onChange={updateField('confirmPassword')}
          isVisible={visiblePasswordField === 'confirmPassword'}
          onToggleVisibility={() =>
            setVisiblePasswordField((currentValue) =>
              currentValue === 'confirmPassword' ? '' : 'confirmPassword')}
          autoComplete="new-password"
          error={errors.confirmPassword}
        />
      </div>

      <div className="pt-0">
        <FormButton type="submit" isLoading={isSubmitting}>
          CREATE ACCOUNT
        </FormButton>
      </div>
    </form>
  );
}
