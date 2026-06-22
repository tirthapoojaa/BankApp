const tabs = [
  { id: 'login', label: 'SIGN IN' },
  { id: 'customer', label: 'NEW CUSTOMER' },
  { id: 'employee', label: 'NEW EMPLOYEE' }
];

export default function TabBar({ activeTab, onChange }) {
  return (
    <div className="grid grid-cols-1 gap-1 rounded-2xl bg-slate-100 p-1 sm:grid-cols-3" role="tablist">
      {tabs.map((tab) => {
        const isActive = activeTab === tab.id;

        return (
          <button
            key={tab.id}
            type="button"
            role="tab"
            aria-selected={isActive}
            onClick={() => onChange(tab.id)}
            className={`rounded-xl px-4 py-3 text-sm font-semibold transition-all duration-200 ${
              isActive
                ? 'bg-white text-[#173A6E] shadow-sm'
                : 'text-brutal-muted hover:bg-white/70 hover:text-brutal-text'
            }`}
          >
            {tab.label}
          </button>
        );
      })}
    </div>
  );
}
