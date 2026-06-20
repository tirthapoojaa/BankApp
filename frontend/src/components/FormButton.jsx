export default function FormButton({
  children,
  isLoading = false,
  className = '',
  ...props
}) {
  return (
    <button
      className={`w-full border-4 border-brutal-border bg-black px-6 py-3 font-heading text-lg uppercase tracking-[0.12em] text-white shadow-brutal transition-transform duration-150 hover:-translate-y-0.5 hover:shadow-brutal-sm active:translate-x-1 active:translate-y-1 active:shadow-brutal-active disabled:cursor-not-allowed disabled:opacity-70 ${className}`}
      disabled={isLoading}
      {...props}
    >
      {isLoading ? 'PLEASE WAIT...' : children}
    </button>
  );
}
