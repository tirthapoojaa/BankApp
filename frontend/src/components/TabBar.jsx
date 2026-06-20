const tabs = [
  { id: 'login', label: 'SIGN IN' },
  { id: 'customer', label: 'NEW CUSTOMER' },
  { id: 'employee', label: 'NEW EMPLOYEE' }
];

export default function TabBar({ activeTab, onChange }) {
  return (
    <div className="grid grid-cols-1 gap-3 sm:grid-cols-3" role="tablist">
      {tabs.map((tab) => {
        const isActive = activeTab === tab.id;

        return (
          <button
            key={tab.id}
            type="button"
            role="tab"
            aria-selected={isActive}
            onClick={() => onChange(tab.id)}
            className={`border-4 border-brutal-border px-3 py-2 font-heading text-xs uppercase tracking-[0.08em] shadow-brutal-sm transition-transform duration-150 hover:-translate-y-0.5 active:translate-x-1 active:translate-y-1 active:shadow-brutal-active sm:text-[0.78rem] ${
              isActive ? 'bg-black text-brutal-card' : 'bg-brutal-card text-black'
            }`}
          >
            {tab.label}
          </button>
        );
      })}
    </div>
  );
}
