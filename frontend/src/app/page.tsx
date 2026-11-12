"use client";

import { useState } from "react";

export default function Home() {
  const [result, setResult] = useState("Backend not checked yet.");

  async function checkBackend() {
    setResult("Checking...");

    try {
      const response = await fetch("/api/health", {
        cache: "no-store",
      });

      if (!response.ok) {
        throw new Error(`HTTP ${response.status}`);
      }

      const data = await response.json();
      setResult(JSON.stringify(data, null, 2));
    } catch {
      setResult("Cannot reach the backend. Check that Spring Boot is running.");
    }
  }

  return (
    <main style={{ padding: "2rem" }}>
      <h1>CampusOps</h1>
      <button onClick={checkBackend}>Check backend</button>
      <pre role="status">{result}</pre>
    </main>
  );
}