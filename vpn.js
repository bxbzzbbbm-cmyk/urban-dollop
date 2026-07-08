const express = require('express');
const cors = require('cors');
const app = express();

app.use(cors());
const PORT = 5902;

app.get('/api/vpn-config', (req, res) => {
    res.json({
        status: 'VPN connected successfully',
        availableCountries: [
            { id: 'md', name: 'Moldova', flag: '🇲🇩' },
            { id: 'us', name: 'United States', flag: '🇺🇸' }
        ],
        vpnIp: `10.8.${Math.floor(Math.random() * 255)}.${Math.floor(Math.random() * 255)}`,
        timestamp: new Date().toISOString()
    });
});

app.listen(PORT, () => {
    console.log(`API listening at http://localhost:${PORT}`);
});
