import BackgroundPattern from '../components/BackgroundPattern.jsx';
import LogoutButton from '../components/LogoutButton.jsx';

export default function Dashboard({ title, description }) {
  return (
    <BackgroundPattern>
      <section className="mx-auto w-full max-w-xl rounded-3xl bg-white p-8 shadow-brutal">
        <div className="space-y-6">
          <div className="text-center">
            <p className="font-heading text-[clamp(2.1rem,7vw,3.5rem)] font-bold leading-tight tracking-[-0.04em] text-brutal-text">
              {title}
            </p>
            <p className="mt-4 text-sm font-medium leading-6 text-brutal-muted">
              {description}
            </p>
          </div>

          <LogoutButton className="h-12 w-full rounded-[10px] bg-[#102A56] px-6 text-sm font-semibold text-white shadow-sm transition duration-200 hover:-translate-y-0.5 hover:bg-[#173A6E] hover:shadow-md disabled:cursor-not-allowed disabled:opacity-60" />
        </div>
      </section>
    </BackgroundPattern>
  );
}
