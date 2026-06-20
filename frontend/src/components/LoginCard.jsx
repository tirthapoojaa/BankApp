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
    <section className="mx-auto w-[624px] max-w-[90vw] brutal-box bg-brutal-card">
      <div className="space-y-6 px-5 py-6 sm:px-8 sm:py-8">
        <CardHeader />
        <TabBar activeTab={activeTab} onChange={handleTabChange} />

        {successMessage && (
          <div className="border-4 border-brutal-border bg-black px-4 py-2 text-sm font-bold uppercase tracking-[0.08em] text-brutal-card shadow-brutal-sm">
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
