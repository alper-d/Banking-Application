import { useEffect, useState } from "react";
import { getBalance, deposit, withdraw, transfer } from "../services/api_operations";
import { useNavigate } from "react-router-dom";
import { Box, Typography, Button, TextField, Paper } from "@mui/material";

export default function Dashboard() {
  const [balance, setBalance] = useState(0);
  const [amount, setAmount] = useState("");
  const [amountToTransfer, setAmountToTransfer] = useState("");
  const [targetUser, setTargetUser] = useState("");
  const [message, setMessage] = useState("");
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  const navigate = useNavigate();
  useEffect(() => {
    loadBalance();
  }, []);

  const loadBalance = async () => {
    try {
      const res = await getBalance();
      setBalance(res.data.balance);
    } catch (err) {
      checkAuth(err);
      setMessage("Error loading balance");
    }
  };

  const handleDeposit = async () => {
    try {
      await deposit(parseFloat(amount));
      setMessage("Deposit successful");
      loadBalance();
    } catch (err) {
      checkAuth(err);
      setMessage("Deposit failed: " + err.response.data);
    }
  };

  const handleWithdraw = async () => {
    try {
      await withdraw(parseFloat(amount));
      setMessage("Withdrawal successful");
      loadBalance();
    } catch (err) {
      checkAuth(err);
      setMessage("Withdraw failed: " + err.response.data);
    }
  };

  const handleTransfer = async () => {
    try {
      await transfer(targetUser, parseFloat(amountToTransfer));
      setMessage("Transfer successful");
      loadBalance();
    } catch (err) {
      checkAuth(err);
      setMessage("Transfer failed: " + err.response.data);
    }
  };

  const checkAuth = async (err) => {
    if (err.status === 401) {
      navigate("/login");
    }
  }

return (
    <Box
      sx={{
        minHeight: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "flex-start",
        backgroundColor: "grey.100",
        pt: 6,
      }}
    >
      <Paper sx={{ p: 4, width: 400 }}>
        <Typography variant="h5" gutterBottom align="center">
          Bank Dashboard
        </Typography>
        <Typography variant="h6" gutterBottom>
          Balance: €{balance}
        </Typography>

        <TextField
          label="Amount"
          type="number"
          fullWidth
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          error={amount !== "" && parseFloat(amount) <= 0}
          helperText={
            amount !== "" && parseFloat(amount) <= 0
              ? "Amount must be greater than 0"
              : ""
          }
          sx={{ mb: 2 }}
        />
        <Box sx={{ display: "flex", gap: 1, mb: 2 }}>
          <Button variant="contained" color="primary" onClick={handleDeposit} fullWidth>
            Deposit
          </Button>
          <Button variant="contained" color="secondary" onClick={handleWithdraw} fullWidth>
            Withdraw
          </Button>
        </Box>

        <TextField
          label="Amount"
          type="number"
          fullWidth
          value={amountToTransfer}
          onChange={(e) => setAmountToTransfer(e.target.value)}
          error={amountToTransfer !== "" && parseFloat(amountToTransfer) <= 0}
          helperText={
            amountToTransfer !== "" && parseFloat(amountToTransfer) <= 0
              ? "Amount must be greater than 0"
              : ""
          }
          sx={{ mb: 2 }}
        />
        <TextField
          label="Send to Email"
          fullWidth
          value={targetUser}
          onChange={(e) => setTargetUser(e.target.value)}
          sx={{ mb: 2 }}
          helperText={
            targetUser !== "" && !emailRegex.test(targetUser)
              ? "Please enter a valid email address"
              : ""
          }
        />
        <Button variant="contained" color="success" onClick={handleTransfer} fullWidth>
          Send
        </Button>

        {message && (
          <Typography color="error" align="center" sx={{ mt: 2 }}>
            {message}
          </Typography>
        )}
      </Paper>
    </Box>
  );
}
