/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,jsx}'],
  theme: {
    extend: {
      colors: {
        brutal: {
          background: '#D8D8D8',
          card: '#D8FF00',
          accent: '#000000',
          border: '#000000',
          text: '#000000',
          muted: '#333333',
          input: '#FFFFFF'
        }
      },
      fontFamily: {
        heading: ['"Barlow Condensed"', 'sans-serif'],
        body: ['Inter', 'sans-serif']
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
