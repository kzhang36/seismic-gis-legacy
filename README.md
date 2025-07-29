# Seismic GIS Legacy 🌏

**Archived WebGIS Platform for Earthquake Monitoring and Visualization**

> This is the legacy version of a real-time earthquake data platform built with a classic full-stack architecture. It supports historical data analysis, real-time monitoring, and basic visual analytics.  
> 👉 The modern and lightweight refactor is available at: [seismic-gis-light](https://github.com/kzhang36/seismic-gis-light)

---

## 🧭 Project Summary

This project visualizes earthquake activity across China and surrounding regions. It fetches and displays seismic data on an interactive WebGIS interface, with features like:

- Real-time earthquake radar
- Historical seismic heatmaps
- Regional statistics and magnitude filtering
- Timeline playback (2009–2019)
- Custom UI panels for earthquake alerts

---

## 🛠️ Tech Stack

| Layer        | Technology Used                         |
|-------------|------------------------------------------|
| Frontend    | Vue2, OpenLayers, ECharts, Element-UI    |
| Backend     | Spring Boot, MyBatis Plus, GeoTools      |
| Mapping     | GeoServer, ArcGIS Server                 |
| Database    | PostgreSQL, PostGIS                      |
| Scripts     | Python (data fetching and preprocessing) |

---

## 📁 Folder Structure

```bash
seismic-gis-legacy/
├── legacy-backend-java/       # Spring Boot + GeoTools backend
├── legacy-frontend-vue2/      # Vue2 + OpenLayers WebGIS UI
├── legacy-fetch/              # Python scripts for fetching seismic data
├── db-schema-legacy/          # SQL files for historical data + schema
├── image/                     # Project screenshots and mockups
├── 定时获取地震数据.html        # Static prototype for early testing
└── README.md
