# Tymed-Dos Frontend

React + TypeScript frontend for the Tymed-Dos historical photo archive system, built with Vite.

## Features

- **React 18** with TypeScript for type-safe development
- **Vite** for fast development and optimized builds
- **Tailwind CSS** for modern, responsive styling
- **Timeline Component** for chronological photo browsing
- **Map Component** ready for Mapbox GL JS integration
- **Component-based architecture** for maintainability

## Prerequisites

- Node.js 18 or higher
- npm or yarn package manager

## Getting Started

### Installation

```bash
# Install dependencies
npm install
```

### Configuration

Create a `.env` file in the frontend directory:

```bash
cp .env.example .env
```

Edit `.env` and add your configuration:

```env
# Optional: Mapbox API Token for map visualization
VITE_MAPBOX_TOKEN=your_mapbox_token_here

# Backend API URL
VITE_API_URL=http://localhost:8000
```

### Development

Start the development server:

```bash
npm run dev
```

The application will be available at http://localhost:5173

### Build

Build for production:

```bash
npm run build
```

The optimized build will be in the `dist/` directory.

### Preview Production Build

Preview the production build locally:

```bash
npm run preview
```

## Project Structure

```
frontend/
├── src/
│   ├── components/          # Reusable React components
│   │   ├── Timeline.tsx    # Timeline component
│   │   └── Map.tsx         # Map component
│   ├── pages/              # Page components
│   ├── types/              # TypeScript type definitions
│   │   └── Photo.ts       # Photo data types
│   ├── utils/              # Utility functions
│   ├── App.tsx             # Main application component
│   ├── main.tsx            # Application entry point
│   └── index.css           # Global styles with Tailwind
├── public/                 # Static assets
├── index.html              # HTML template
├── package.json            # Dependencies and scripts
├── tsconfig.json           # TypeScript configuration
├── vite.config.ts          # Vite configuration
├── tailwind.config.js      # Tailwind CSS configuration
└── postcss.config.js       # PostCSS configuration
```

## Components

### Timeline Component

Located in `src/components/Timeline.tsx`

Displays photos in chronological order with date filtering capabilities.

```tsx
import Timeline from './components/Timeline';

<Timeline onDateSelect={(date) => console.log(date)} />
```

### Map Component

Located in `src/components/Map.tsx`

Interactive map component for displaying photo locations. Ready for Mapbox GL JS integration.

```tsx
import Map from './components/Map';

<Map onLocationSelect={(lat, lng) => console.log(lat, lng)} />
```

## Available Scripts

- `npm run dev` - Start development server with hot reload
- `npm run build` - Build for production
- `npm run preview` - Preview production build

## Resources

- [Vite Documentation](https://vitejs.dev/)
- [React Documentation](https://react.dev/)
- [TypeScript Documentation](https://www.typescriptlang.org/docs/)
- [Tailwind CSS Documentation](https://tailwindcss.com/docs)
