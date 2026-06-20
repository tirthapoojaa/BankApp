export default function BackgroundPattern({ children }) {
  return (
    <main
      className="relative flex min-h-screen items-center justify-center overflow-hidden bg-brutal-background p-5 font-body sm:p-8"
      style={{
        backgroundImage: `
          linear-gradient(135deg, rgba(0, 0, 0, 0.045) 1px, transparent 1px),
          linear-gradient(45deg, rgba(255, 255, 255, 0.22) 1px, transparent 1px)
        `,
        backgroundSize: '32px 32px'
      }}
    >
      <div className="relative z-10 w-full">{children}</div>
    </main>
  );
}
