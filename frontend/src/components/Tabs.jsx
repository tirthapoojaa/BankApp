const tabs = [
  { id: 'signIn', label: 'SIGN IN' },
  { id: 'newCustomer', label: 'NEW CUSTOMER' },
  { id: 'newEmployee', label: 'NEW EMPLOYEE' }
];

export default function Tabs({ selectedTab, onSelectTab }) {
  return (
    <div className="grid grid-cols-1 gap-3 sm:grid-cols-3" role="tablist">
      {tabs.map((tab) => {
        const isActive = selectedTab === tab.id;

        return (
          <button
            key={tab.id}
            type="button"
            role="tab"
            aria-selected={isActive}
            onClick={() => onSelectTab(tab.id)}
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
