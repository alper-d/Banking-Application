import logo from './logo.svg';
import './App.css';
import { Box, Typography, Button, Paper, Container } from "@mui/material";
import { useNavigate } from "react-router-dom";
import Monitor from './pages/Monitor';
function App() {
  const navigate = useNavigate();
return (
  <Box>
    <Box
      sx={{
        minHeight: "70vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        background: "linear-gradient(to right, #4f46e5, #6366f1)", // modern gradient
      }}
    >
      <Paper
        sx={{
          p: 6,
          maxWidth: 500,
          textAlign: "center",
          borderRadius: 4,
          boxShadow: 3,
        }}
      >
        <Typography variant="h3" gutterBottom sx={{ fontWeight: "bold", color: "#1e293b" }}>
          Welcome to FastBank
        </Typography>
        <Typography variant="body1" sx={{ mb: 4, color: "#475569" }}>
          Manage your money easily. Deposit, withdraw, and send money securely in one place.
        </Typography>

        <Container sx={{ display: "flex", justifyContent: "center", gap: 2 }}>
          <Button
            variant="contained"
            color="primary"
            onClick={() => navigate("/login")}
          >
            Login
          </Button>
          <Button
            variant="outlined"
            color="primary"
            onClick={() => navigate("/register")}
          >
            Register
          </Button>
        </Container>
      </Paper>
    </Box>
    <Box>

    <Monitor>
    </Monitor>
    </Box>
  </Box>
  );
}

export default App;
