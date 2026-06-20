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
      <span className="mb-1 block font-heading text-sm uppercase tracking-[0.08em]">
        {label}
      </span>
      <select
        id={id}
        value={value}
        onChange={onChange}
        disabled={disabled}
        className="brutal-control appearance-none disabled:cursor-not-allowed disabled:bg-white/70"
      >
        {options.map((option) => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
      </select>
      {error && (
        <span className="mt-1 block text-sm font-bold text-red-800">
          {error}
        </span>
      )}
    </label>
  );
}
