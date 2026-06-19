export default function Button({ children, className = '', ...props }) {
  return (
    <button
      className={`w-full border-4 border-brutal-border bg-brutal-accent px-6 py-4 font-heading text-lg tracking-[0.12em] text-brutal-text shadow-brutal transition-transform duration-150 hover:-translate-y-1 active:translate-x-1 active:translate-y-1 active:shadow-brutal-active ${className}`}
      {...props}
    >
      {children}
    </button>
  );
}
