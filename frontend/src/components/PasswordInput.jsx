import { Eye, EyeOff } from 'lucide-react';
import Input from './Input.jsx';

export default function PasswordInput({
  id,
  label,
  value,
  onChange,
  isVisible,
  onToggleVisibility,
  error,
  autoComplete = 'current-password'
}) {
  const Icon = isVisible ? EyeOff : Eye;

  return (
    <Input id={id} label={label}>
      <div className="relative">
        <input
          id={id}
          type={isVisible ? 'text' : 'password'}
          value={value}
          onChange={onChange}
          placeholder="Enter your password"
          autoComplete={autoComplete}
          className="brutal-control pr-14"
        />
        <button
          type="button"
          aria-label={isVisible ? 'Hide password' : 'Show password'}
          onClick={onToggleVisibility}
          className="absolute right-3 top-1/2 flex h-9 w-9 -translate-y-1/2 items-center justify-center border-2 border-brutal-border bg-brutal-accent transition-transform duration-150 hover:-translate-y-[60%] active:translate-y-[-40%]"
        >
          <Icon size={20} strokeWidth={3} />
        </button>
      </div>
      {error && (
        <span className="mt-2 block text-sm font-bold text-red-800">
          {error}
        </span>
      )}
    </Input>
  );
}
