// api.js

const API_BASE_URL = 'http://localhost:8081';

async function apiCall(endpoint, method = 'GET', data) {
    try {
        const options = {
            method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: data ? JSON.stringify(data) : null
        };
        const response = await fetch(`${API_BASE_URL}/${endpoint}`, options);
        if (!response.ok) {
            const errorMessage = await response.text();
            throw new Error(`HTTP error! Status: ${response.status}. Error message: ${errorMessage}`);
          }
        
          // Si la respuesta no contiene contenido, devolver un objeto vacío
          if (response.status === 204) {
            return {};
          }
        
          return await response.json();
    } catch (error) {
        console.error('Error:', error);
        throw error;
    }
}

export { apiCall };
