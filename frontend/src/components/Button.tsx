import React from 'react';

interface ButtonProps {
  children: React.ReactNode;
  onClick?: () => void;
}

export const Button: React.FC<ButtonProps> = ({ children, onClick }) => (
  <button className="px-4 py-2 bg-blue-600 text-white rounded" onClick={onClick}>
    {children}
  </button>
);
