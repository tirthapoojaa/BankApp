/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,jsx}'],
  theme: {
    extend: {
      colors: {
        brutal: {
          background: '#C9C4FF',
          card: '#F5B7CC',
          accent: '#FFF000',
          border: '#000000',
          text: '#000000',
          muted: '#666666',
          input: '#FFFFFF'
        }
      },
      fontFamily: {
        heading: ['"Archivo Black"', 'sans-serif'],
        body: ['"DM Sans"', 'sans-serif']
      },
      boxShadow: {
        brutal: '8px 8px 0 #000000',
        'brutal-sm': '5px 5px 0 #000000',
        'brutal-active': '3px 3px 0 #000000'
      }
    }
  },
  plugins: []
};
