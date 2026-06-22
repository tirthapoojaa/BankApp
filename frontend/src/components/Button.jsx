export default function Button({ children, className = '', ...props }) {
  return (
    <button
      className={`h-12 w-full rounded-[10px] bg-[#102A56] px-6 text-sm font-semibold text-white shadow-sm transition duration-200 hover:-translate-y-0.5 hover:bg-[#173A6E] hover:shadow-md active:translate-y-0 ${className}`}
      {...props}
    >
      {children}
    </button>
  );
}
