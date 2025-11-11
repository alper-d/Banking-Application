import axios from "axios";

const API_BASE = "http://localhost:9090/api/account";

export const getAllAccounts = () => {
  return axios.get(`${API_BASE}`);
};
export const getTransactionsByAccountId = (accountId) => {
  return axios.get(`${API_BASE}/${accountId}/transactions`);
};
