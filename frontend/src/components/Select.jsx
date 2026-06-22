export default function Select({
  id,
  label,
  value,
  onChange,
  options,
  error,
  disabled = false
}) {
  return (
    <label className="block" htmlFor={id}>
      <span className="mb-2 block text-sm font-medium text-brutal-text">
        {label}
      </span>
      <select
        id={id}
        value={value}
        onChange={onChange}
        disabled={disabled}
        className="brutal-control appearance-none disabled:cursor-not-allowed disabled:bg-slate-100"
      >
        {options.map((option) => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
      </select>
      {error && (
        <span className="mt-2 block text-sm font-medium text-[#DC2626]">
          {error}
        </span>
      )}
    </label>
  );
}
