export default function CardHeader() {
  return (
    <header className="space-y-3">
      <p className="text-sm font-semibold uppercase tracking-[0.24em] text-[#C8A34D]">
        BankingApp Online
      </p>
      <h2 className="font-heading text-[clamp(2.6rem,6vw,4.5rem)] font-bold leading-[0.95] tracking-[-0.04em] text-brutal-text">
        Welcome back
      </h2>
      <p className="max-w-xl text-base leading-7 text-brutal-muted">
        Sign in or create a new profile to continue to your banking dashboard.
      </p>
    </header>
  );
}
