import type { Config } from 'tailwindcss';

const config: Config = {
  content: ['../resources/templates/**/*.html'],
  theme: {
    extend: {
      colors: {
        white: '#ffffff',
        black: '#000000',
        transparent: 'transparent',
      },

      fontFamily: {
        inter: ['Inter', 'sans-serif'],
      },

      maxHeight: {
        46: '11.5rem',
        128: '32rem',
        xl: '36rem',
        '2xl': '42rem',
        '3xl': '48rem',
      },

      divideWidth: {
        0.25: '0.0625rem',
      },

      keyframes: {
        wiggle: {
          '0%, 100%': { transform: 'rotate(0deg)' },
          '33%': { transform: 'rotate(20deg)' },
          '66%': { transform: 'rotate(-25deg)' },
        },
        fadeIn: {
          '0%': {
            opacity: '0',
            transform: 'translateY(4px)',
          },
          '100%': {
            opacity: '1',
            transform: 'translateY(0)',
          },
        },
      },

      animation: {
        wiggle: 'wiggle 0.5s ease-in-out',
        'fade-in': 'fadeIn 0.2s ease-out forwards',
      },

      backgroundImage: {
        'sidebar-item':
          'linear-gradient(90deg, rgba(195, 194, 255, 0.70) 0%, rgba(195, 194, 255, 0.51) 19.95%, rgba(195, 194, 255, 0.24) 125.53%)',
        graphics: "url('/img/illustrations/bg-graphics.svg')",
        'graphics-2': "url('/img/illustrations/bg-graphics-2.svg')",
      },
    },
  },
  plugins: [],
};

export default config;
