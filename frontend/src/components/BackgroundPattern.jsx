const pattern = encodeURIComponent(`
<svg xmlns="http://www.w3.org/2000/svg" width="180" height="180" viewBox="0 0 180 180">
  <rect width="180" height="180" fill="#C9C4FF"/>
  <circle cx="34" cy="34" r="22" fill="#FFF000" stroke="#000" stroke-width="4"/>
  <text x="34" y="43" text-anchor="middle" font-size="28" font-family="Arial Black, Arial" fill="#000">£</text>
  <circle cx="134" cy="112" r="22" fill="#FFF000" stroke="#000" stroke-width="4"/>
  <text x="134" y="121" text-anchor="middle" font-size="28" font-family="Arial Black, Arial" fill="#000">£</text>
</svg>`);

export default function BackgroundPattern({ children }) {
  return (
    <main
      className="relative flex min-h-screen items-center justify-center overflow-hidden bg-brutal-background p-5 font-body sm:p-8"
      style={{
        backgroundImage: `url("data:image/svg+xml,${pattern}")`,
        backgroundRepeat: 'repeat',
        backgroundSize: '180px 180px'
      }}
    >
      <div className="absolute inset-0 bg-brutal-background/20" />
      <div className="relative z-10 w-full">{children}</div>
    </main>
  );
}
