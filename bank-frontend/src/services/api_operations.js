import axios from "axios";

const API_URL = "http://localhost:9090/api/transaction";

const authHeader = () => ({
  headers: { Authorization: `Bearer ${localStorage.getItem("jwt")}` },
});

export const getBalance = () => {
  return axios.get(`${API_URL}/balance`, authHeader());
};

export const deposit = (amount) => {
  return axios.post(`${API_URL}/deposit`, { amount }, authHeader());
};

export const withdraw = (amount) => {
  return axios.post(`${API_URL}/withdraw`, { amount }, authHeader());
};

export const transfer = (toEmail, amount) => {
  return axios.post(`${API_URL}/send`, { toEmail, amount }, authHeader());
};
