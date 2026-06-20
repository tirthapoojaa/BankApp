import BackgroundPattern from '../components/BackgroundPattern.jsx';
import LogoutButton from '../components/LogoutButton.jsx';

export default function Dashboard({ title, description }) {
  return (
    <BackgroundPattern>
      <section className="mx-auto w-[520px] max-w-[90vw] brutal-box bg-brutal-card">
        <div className="space-y-6 px-5 py-7 sm:px-8 sm:py-9">
          <div className="text-center">
            <p className="font-heading text-[clamp(2.1rem,7vw,3.5rem)] leading-none tracking-[-0.04em] text-brutal-text">
              {title}
            </p>
            <p className="mt-4 font-bold text-brutal-muted">
              {description}
            </p>
          </div>

          <LogoutButton className="w-full border-4 border-brutal-border bg-black px-6 py-3 font-heading text-lg uppercase tracking-[0.12em] text-white shadow-brutal transition-transform duration-150 hover:-translate-y-0.5 hover:shadow-brutal-sm active:translate-x-1 active:translate-y-1 active:shadow-brutal-active disabled:cursor-not-allowed disabled:opacity-70" />
        </div>
      </section>
    </BackgroundPattern>
  );
}
