export default function BackgroundPattern({ children }) {
  return (
    <main className="grid min-h-screen bg-brutal-background font-body text-brutal-text lg:grid-cols-[minmax(360px,0.9fr)_minmax(520px,1.1fr)]">
      <section className="relative hidden overflow-hidden bg-[#102A56] px-10 py-12 text-white lg:flex lg:flex-col">
        <div className="absolute inset-0 bg-[radial-gradient(circle_at_20%_20%,rgba(200,163,77,0.24),transparent_34%),linear-gradient(135deg,rgba(255,255,255,0.08),transparent_46%)]" />
        <div className="relative z-10 flex h-full flex-col">
          <div className="flex items-center gap-3">
            <span className="flex h-11 w-11 items-center justify-center rounded-2xl bg-[#C8A34D] text-lg font-extrabold text-[#102A56]">
              BA
            </span>
            <span className="text-sm font-semibold uppercase tracking-[0.24em] text-white/80">
              BankingApp
            </span>
          </div>

          <div className="my-auto max-w-xl space-y-7">
            <p className="text-sm font-semibold uppercase tracking-[0.28em] text-[#C8A34D]">
              Secure Digital Banking
            </p>
            <h1 className="font-heading text-[clamp(3.4rem,6vw,6.8rem)] font-bold leading-[0.95] tracking-[-0.05em]">
              Manage money with clarity and control.
            </h1>
            <p className="max-w-lg text-lg leading-8 text-white/76">
              One trusted portal for customers and employees to access accounts,
              transactions, branches, and daily banking operations.
            </p>

            <div className="grid gap-4 text-sm text-white/82">
              <div className="rounded-2xl border border-white/15 bg-white/8 p-4 backdrop-blur">
                Bank-grade session security with role-based access control.
              </div>
              <div className="rounded-2xl border border-white/15 bg-white/8 p-4 backdrop-blur">
                Protected account workflows for customers and operations teams.
              </div>
            </div>
          </div>

          <p className="relative z-10 text-xs uppercase tracking-[0.22em] text-white/50">
            Regulated demo banking experience. Secure access only.
          </p>
        </div>
      </section>

      <section className="flex min-h-screen items-center justify-center px-5 py-8 sm:px-8 lg:px-14">
        <div className="w-full max-w-[720px]">{children}</div>
      </section>
    </main>
  );
}
