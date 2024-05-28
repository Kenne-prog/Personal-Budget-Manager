module.exports = {
    transform: {
      '^.+\\.jsx?$': 'babel-jest'
    },
    moduleNameMapper: {
      '^axios$': 'axios/dist/node/axios.cjs',
      '\\.(css|less|sass|scss)$': 'identity-obj-proxy'
    },
    testEnvironment: 'jsdom'
  };
  