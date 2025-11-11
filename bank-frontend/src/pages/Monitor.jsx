import { useEffect, useState } from "react";
import { Box, Typography, Paper, Divider } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
import { getAllAccounts, getTransactionsByAccountId } from "../services/api_accounts";

export default function Monitor() {
  const [accounts, setAccounts] = useState([]);
  const [transactions, setTransactions] = useState([]);
  const [selectedAccount, setSelectedAccount] = useState(null);

  useEffect(() => {
    loadAccounts();
  }, []);

  const loadAccounts = async () => {
    try {
      const res = await getAllAccounts();
      setAccounts(res.data);
    } catch (err) {
      console.error("Failed to fetch accounts", err);
    }
  };

  const handleRowClick = async (params) => {
    const account = params.row;
    setSelectedAccount(account);
    try {
      const res = await getTransactionsByAccountId(account.id);
      setTransactions(res.data);
      console.log(res.data)
    } catch (err) {
      console.error("Failed to fetch transactions", err);
    }
  };

  const accountColumns = [
    { field: "id", headerName: "ID", width: 70 },
    { field: "email", headerName: "Email", flex: 1.5 },
    {
      field: "balance",
      headerName: "Balance (€)",
      type: "number",
      flex: 1,
    },
  ];

  const transactionColumns = [
    { field: "id", headerName: "ID", width: 70 },
    {
      field: "amount",
      headerName: "Amount (€)",
      type: "number",
      flex: 1,
    },
    {
      field: "transactionTime",
      headerName: "Date",
      flex: 1.5,
      valueFormatter: (params) =>
        new Date(params).toLocaleString("en-GB"),
    },
    {
      field: "toEmail",
      headerName: "Recipient",
      flex: 1.5,
    },
  ];

  return (
    <Box
      sx={{
        minHeight: "100vh",
        backgroundColor: "grey.100",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        p: 4,
      }}
    >
      <Paper sx={{ p: 4, width: "100%", maxWidth: 900 }}>
        <Typography variant="h5" gutterBottom align="center">
          Account Monitoring
        </Typography>

        <div style={{ height: 400, width: "100%" }}>
          <DataGrid
            rows={accounts}
            columns={accountColumns}
            pageSize={10}
            rowsPerPageOptions={[10]}
            onRowClick={handleRowClick}
          />
        </div>

        {selectedAccount && (
          <>
            <Divider sx={{ my: 3 }} />
            <Typography variant="h6" gutterBottom>
              Transactions for {selectedAccount.username}
            </Typography>
            <div style={{ height: 300, width: "100%" }}>
              <DataGrid
                rows={transactions}
                columns={transactionColumns}
                pageSize={5}
                rowsPerPageOptions={[5, 10]}
              />
            </div>
          </>
        )}
      </Paper>
    </Box>
  );
}
