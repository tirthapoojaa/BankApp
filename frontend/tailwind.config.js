/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,jsx}'],
  theme: {
    extend: {
      colors: {
        brutal: {
          background: '#F4F6F8',
          card: '#FFFFFF',
          accent: '#173A6E',
          border: '#D8DEE8',
          text: '#1E293B',
          muted: '#64748B',
          input: '#FFFFFF'
        }
      },
      fontFamily: {
        heading: ['"Playfair Display"', 'serif'],
        body: ['Inter', 'sans-serif']
      },
      boxShadow: {
        brutal: '0 24px 70px rgba(16, 42, 86, 0.12)',
        'brutal-sm': '0 10px 28px rgba(16, 42, 86, 0.10)',
        'brutal-active': '0 4px 14px rgba(16, 42, 86, 0.10)'
      }
    }
  },
  plugins: []
};
