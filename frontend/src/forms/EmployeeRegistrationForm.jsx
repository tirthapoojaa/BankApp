import { useEffect, useState } from 'react';
import {
  fetchBranches,
  registerEmployee
} from '../api/bankingApi.js';
import FormButton from '../components/FormButton.jsx';
import Input from '../components/Input.jsx';
import PasswordInput from '../components/PasswordInput.jsx';
import Select from '../components/Select.jsx';
import {
  required,
  validateMinimumPassword,
  validatePasswordMatch
} from './formValidation.js';

const designationOptions = [
  { value: 'BRANCH_MANAGER', label: 'Branch Manager' },
  { value: 'RELATIONSHIP_MANAGER', label: 'Relationship Manager' },
  { value: 'CASHIER', label: 'Cashier' }
];

const initialFormData = {
  employeeId: '',
  fullName: '',
  userId: '',
  password: '',
  confirmPassword: '',
  employeeRole: 'CASHIER',
  salary: '',
  branchId: ''
};

export default function EmployeeRegistrationForm({ onSuccess }) {
  const [formData, setFormData] = useState(initialFormData);
  const [branches, setBranches] = useState([]);
  const [errors, setErrors] = useState({});
  const [formMessage, setFormMessage] = useState('');
  const [visiblePasswordField, setVisiblePasswordField] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [isLoadingBranches, setIsLoadingBranches] = useState(true);

  useEffect(() => {
    let isMounted = true;

    fetchBranches()
      .then((branchList) => {
        if (!isMounted) {
          return;
        }

        setBranches(branchList);
        setFormData((currentData) => ({
          ...currentData,
          branchId: branchList[0]?.branchId
            ? String(branchList[0].branchId)
            : ''
        }));
      })
      .catch(() => {
        if (isMounted) {
          setFormMessage('Unable to load branches.');
        }
      })
      .finally(() => {
        if (isMounted) {
          setIsLoadingBranches(false);
        }
      });

    return () => {
      isMounted = false;
    };
  }, []);

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
      employeeId: required(formData.employeeId, 'Employee ID is required.'),
      fullName: required(formData.fullName, 'Full name is required.'),
      userId: required(formData.userId, 'User ID is required.'),
      password: validateMinimumPassword(formData.password),
      confirmPassword: validatePasswordMatch(
        formData.password,
        formData.confirmPassword
      ),
      employeeRole: required(formData.employeeRole, 'Designation is required.'),
      salary: required(formData.salary, 'Salary is required.'),
      branchId: required(formData.branchId, 'Branch is required.')
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
      const result = await registerEmployee(formData);
      if (result.success) {
        setFormData({
          ...initialFormData,
          branchId: branches[0]?.branchId ? String(branches[0].branchId) : ''
        });
        onSuccess(result.message);
        return;
      }

      setFormMessage(result.message);
    } catch (error) {
      setFormMessage('Employee registration failed. Please try again.');
    } finally {
      setIsSubmitting(false);
    }
  };

  const branchOptions = branches.length > 0
    ? branches.map((branch) => ({
      value: String(branch.branchId),
      label: `${branch.branchName} - ${branch.bankName}`
    }))
    : [{ value: '', label: 'No branches available' }];

  return (
    <form className="space-y-5" onSubmit={handleSubmit}>
      {formMessage && (
        <div className="border-4 border-brutal-border bg-white px-4 py-3 text-sm font-bold text-red-800 shadow-brutal-sm">
          {formMessage}
        </div>
      )}

      <Input
        id="employeeId"
        label="Employee ID"
        type="number"
        min="1"
        value={formData.employeeId}
        onChange={updateField('employeeId')}
        placeholder="Enter employee ID"
        error={errors.employeeId}
      />

      <Input
        id="employeeFullName"
        label="Full Name"
        value={formData.fullName}
        onChange={updateField('fullName')}
        placeholder="Enter full name"
        autoComplete="name"
        error={errors.fullName}
      />

      <Input
        id="employeeUserId"
        label="User ID"
        value={formData.userId}
        onChange={updateField('userId')}
        placeholder="Create user ID"
        autoComplete="username"
        error={errors.userId}
      />

      <PasswordInput
        id="employeePassword"
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
        id="employeeConfirmPassword"
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

      <Select
        id="employeeDesignation"
        label="Designation"
        value={formData.employeeRole}
        onChange={updateField('employeeRole')}
        options={designationOptions}
        error={errors.employeeRole}
      />

      <Input
        id="employeeSalary"
        label="Salary"
        type="number"
        min="0"
        step="0.01"
        value={formData.salary}
        onChange={updateField('salary')}
        placeholder="Enter salary"
        error={errors.salary}
      />

      <Select
        id="employeeBranch"
        label="Branch"
        value={formData.branchId}
        onChange={updateField('branchId')}
        options={branchOptions}
        disabled={isLoadingBranches || branches.length === 0}
        error={errors.branchId}
      />

      <div className="pt-2">
        <FormButton
          type="submit"
          isLoading={isSubmitting || isLoadingBranches}
        >
          REGISTER EMPLOYEE
        </FormButton>
      </div>
    </form>
  );
}
