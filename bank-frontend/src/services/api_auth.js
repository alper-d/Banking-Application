import axios from "axios";

const API_URL = "http://localhost:9090/api";

export const registerUser = (userData) => {
  return axios.post(`${API_URL}/account`, userData);
};

export const loginUser = (credentials) => {
  return axios.post(`${API_URL}/auth/login`, credentials);
};
