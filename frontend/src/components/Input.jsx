export default function Input({
  id,
  label,
  type = 'text',
  value,
  onChange,
  placeholder,
  error,
  min,
  step,
  autoComplete,
  children
}) {
  return (
    <label className="block" htmlFor={id}>
      <span className="mb-2 block font-heading text-sm tracking-[0.08em]">
        {label}
      </span>
      {children || (
        <input
          id={id}
          type={type}
          value={value}
          onChange={onChange}
          placeholder={placeholder}
          min={min}
          step={step}
          autoComplete={autoComplete}
          className="brutal-control"
        />
      )}
      {error && (
        <span className="mt-2 block text-sm font-bold text-red-800">
          {error}
        </span>
      )}
    </label>
  );
}
