import { useState } from 'react';
import CardFooter from './CardFooter.jsx';
import CardHeader from './CardHeader.jsx';
import TabBar from './TabBar.jsx';
import CustomerRegistrationForm from '../forms/CustomerRegistrationForm.jsx';
import EmployeeRegistrationForm from '../forms/EmployeeRegistrationForm.jsx';
import LoginForm from '../forms/LoginForm.jsx';

const formComponents = {
  login: LoginForm,
  customer: CustomerRegistrationForm,
  employee: EmployeeRegistrationForm
};

export default function LoginCard() {
  const [activeTab, setActiveTab] = useState('login');
  const [successMessage, setSuccessMessage] = useState('');

  const ActiveForm = formComponents[activeTab];

  const handleTabChange = (tab) => {
    setActiveTab(tab);
    setSuccessMessage('');
  };

  const handleRegistrationSuccess = (message) => {
    setSuccessMessage(message);
    setActiveTab('login');
  };

  return (
    <section className="mx-auto w-full">
      <div className="space-y-8">
        <CardHeader />
        <TabBar activeTab={activeTab} onChange={handleTabChange} />

        {successMessage && (
          <div className="rounded-2xl border border-green-200 bg-green-50 px-4 py-3 text-sm font-semibold text-green-700 shadow-sm">
            {successMessage}
          </div>
        )}

        <div
          key={activeTab}
          className="animate-[tabSwap_240ms_ease-out]"
        >
          <ActiveForm onSuccess={handleRegistrationSuccess} />
        </div>
      </div>

      <CardFooter />
    </section>
  );
}
