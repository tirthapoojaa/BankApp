import { useState } from 'react';
import { submitLogin } from '../api/bankingApi.js';
import FormButton from '../components/FormButton.jsx';
import Input from '../components/Input.jsx';
import PasswordInput from '../components/PasswordInput.jsx';
import Select from '../components/Select.jsx';
import { required } from './formValidation.js';

const roleOptions = [
  { value: 'CUSTOMER', label: 'Customer' },
  { value: 'CASHIER', label: 'Cashier' },
  { value: 'RELATIONSHIP_MANAGER', label: 'Relationship Manager' },
  { value: 'BRANCH_MANAGER', label: 'Branch Manager' },
  { value: 'ADMIN', label: 'Admin' }
];

export default function LoginForm() {
  const [formData, setFormData] = useState({
    userId: '',
    password: '',
    role: 'CUSTOMER'
  });
  const [errors, setErrors] = useState({});
  const [formMessage, setFormMessage] = useState('');
  const [isPasswordVisible, setIsPasswordVisible] = useState(false);
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
      userId: required(formData.userId, 'User ID is required.'),
      password: required(formData.password, 'Password is required.'),
      role: required(formData.role, 'Role is required.')
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
      const result = await submitLogin(formData);
      if (result.success) {
        window.location.assign(result.dashboardPath);
        return;
      }

      setFormMessage(result.message);
    } catch (error) {
      setFormMessage('Unable to sign in. Please try again.');
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

      <Input
        id="userId"
        label="User ID"
        value={formData.userId}
        onChange={updateField('userId')}
        placeholder="Enter your user ID"
        autoComplete="username"
        error={errors.userId}
      />

      <PasswordInput
        id="password"
        label="Password"
        value={formData.password}
        onChange={updateField('password')}
        isVisible={isPasswordVisible}
        onToggleVisibility={() =>
          setIsPasswordVisible((currentValue) => !currentValue)}
        error={errors.password}
      />

      <Select
        id="role"
        label="Role"
        value={formData.role}
        onChange={updateField('role')}
        options={roleOptions}
        error={errors.role}
      />

      <div className="pt-0">
        <FormButton type="submit" isLoading={isSubmitting}>
          SIGN IN
        </FormButton>
      </div>
    </form>
  );
}
