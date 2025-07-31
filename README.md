# 🎯 Habit Tracker Application

A full-stack productivity web application for tracking daily habits, built with React frontend, Spring Boot backend, and MongoDB database.

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   React Frontend │    │ Spring Boot API │    │    MongoDB      │
│     (Port 3000)  │────│   (Port 8080)   │────│  (Port 27017)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │
                       ┌─────────────────┐
                       │  Mongo Express  │
                       │   (Port 8081)   │
                       └─────────────────┘
```

## ✨ Features

- **Habit Management**: Create, update, and delete habits
- **Progress Tracking**: Mark habits as complete for each day
- **Weekly Goals**: Set target frequency per week
- **Progress Visualization**: Visual progress bars and statistics
- **Responsive Design**: Works on desktop and mobile devices
- **Real-time Updates**: Instant feedback on habit completion

## 🚀 Quick Start

### Prerequisites

- Docker and Docker Compose
- Git

### Running the Application

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd habit-tracker-app
   ```

2. **Start all services**
   ```bash
   docker-compose up -d
   ```

3. **Access the application**
   - **Frontend**: http://localhost:3000
   - **Backend API**: http://localhost:8080/api
   - **Mongo Express**: http://localhost:8081
   - **MongoDB**: mongodb://admin:password123@localhost:27017

4. **Stop the application**
   ```bash
   docker-compose down
   ```

## 🛠️ Development Setup

### Backend Development

```bash
cd backend
./mvnw spring-boot:run
```

### Frontend Development

```bash
cd frontend
npm install
npm start
```

### Database Access

- **MongoDB Connection**: `mongodb://admin:password123@localhost:27017/habittracker?authSource=admin`
- **Admin Panel**: http://localhost:8081

## 📁 Project Structure

```
habit-tracker-app/
├── docker-compose.yml          # Main orchestration file
├── .github/workflows/          # CI/CD pipelines
│   ├── backend-ci-cd.yml
│   └── frontend-ci-cd.yml
├── backend/                    # Spring Boot application
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/java/
│       └── com/example/habittracker/
│           ├── HabitTrackerApplication.java
│           ├── controller/
│           ├── model/
│           ├── repository/
│           └── service/
├── frontend/                   # React application
│   ├── Dockerfile
│   ├── nginx.conf
│   ├── package.json
│   ├── public/
│   └── src/
│       ├── App.js
│       ├── App.css
│       └── index.js
└── README.md
```

## 🔧 Technologies Used

### Backend
- **Spring Boot 3.2.0** - Application framework
- **Spring Data MongoDB** - Database integration
- **Lombok** - Reduces boilerplate code
- **Java 17** - Programming language
- **Maven** - Dependency management

### Frontend
- **React 18** - UI framework
- **Axios** - HTTP client
- **CSS3** - Styling with modern features
- **Nginx** - Production web server

### Database & Infrastructure
- **MongoDB 7.0** - NoSQL database
- **Mongo Express** - Database admin interface
- **Docker & Docker Compose** - Containerization
- **GitHub Actions** - CI/CD pipelines

## 🔄 CI/CD Pipeline

### Automated Workflows

1. **Backend Pipeline** (`.github/workflows/backend-ci-cd.yml`)
   - Tests with Maven
   - Builds Docker image
   - Pushes to Docker Hub
   - Security scanning with Trivy

2. **Frontend Pipeline** (`.github/workflows/frontend-ci-cd.yml`)
   - Runs tests and linting
   - Builds production bundle
   - Creates Docker image
   - Lighthouse performance audit

### Setup Instructions

1. **Fork this repository**
2. **Create Docker Hub account** and note your username
3. **Update GitHub Secrets**:
   - `DOCKERHUB_USERNAME`: Your Docker Hub username
   - `DOCKERHUB_TOKEN`: Docker Hub access token
4. **Modify image names** in workflow files:
   - Replace `your-dockerhub-username` with your actual username

## 📊 API Endpoints

### Habits

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/habits` | Get all habits |
| GET | `/api/habits/{id}` | Get habit by ID |
| POST | `/api/habits` | Create new habit |
| PUT | `/api/habits/{id}` | Update habit |
| DELETE | `/api/habits/{id}` | Delete habit |
| POST | `/api/habits/{id}/entries` | Add habit entry |
| GET | `/api/habits/search?name={name}` | Search habits |

### Example Requests

**Create Habit**
```json
POST /api/habits
{
  "name": "Drink 8 glasses of water",
  "description": "Stay hydrated throughout the day",
  "targetFrequency": 7
}
```

**Mark Habit Complete**
```
POST /api/habits/{id}/entries?date=2024-01-15&completed=true&notes=Feeling great
```

## 🐳 Docker Configuration

### Services Overview

- **mongodb**: MongoDB 7.0 with authentication
- **mongo-express**: Web-based MongoDB admin interface
- **backend**: Spring Boot application
- **frontend**: React app served by Nginx

### Environment Variables

```yaml
# MongoDB
MONGO_INITDB_ROOT_USERNAME: admin
MONGO_INITDB_ROOT_PASSWORD: password123

# Spring Boot
SPRING_DATA_MONGODB_URI: mongodb://admin:password123@mongodb:27017/habittracker?authSource=admin
```

## 🔒 Security Features

- **CORS Configuration**: Properly configured for cross-origin requests
- **Input Validation**: Server-side validation using Bean Validation
- **Security Headers**: Nginx configured with security headers
- **Vulnerability Scanning**: Automated security scans in CI/CD

## 🚀 Production Deployment

### Using Docker Hub Images

```bash
# Pull and run latest images
docker pull your-dockerhub-username/habit-tracker-backend:latest
docker pull your-dockerhub-username/habit-tracker-frontend:latest

# Use production docker-compose
docker-compose -f docker-compose.prod.yml up -d
```

### Environment-specific Configuration

Create environment-specific compose files:
- `docker-compose.dev.yml` - Development
- `docker-compose.prod.yml` - Production
- `docker-compose.test.yml` - Testing

## 🧪 Testing

### Backend Tests
```bash
cd backend
./mvnw test
```

### Frontend Tests
```bash
cd frontend
npm test
```

### Integration Tests
```bash
# Start services in test mode
docker-compose -f docker-compose.test.yml up -d

# Run API tests
cd backend
./mvnw verify
```

## 📈 Monitoring & Logging

### Application Logs
```bash
# View backend logs
docker-compose logs -f backend

# View frontend logs
docker-compose logs -f frontend

# View all logs
docker-compose logs -f
```

### Health Checks
- **Backend**: http://localhost:8080/actuator/health
- **Frontend**: http://localhost:3000
- **Database**: Check via Mongo Express

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Troubleshooting

### Common Issues

**MongoDB Connection Issues**
```bash
# Check if MongoDB is running
docker-compose ps mongodb

# View MongoDB logs
docker-compose logs mongodb
```

**Frontend Not Loading**
```bash
# Rebuild frontend image
docker-compose build frontend
docker-compose up -d frontend
```

**Backend API Not Responding**
```bash
# Check backend logs
docker-compose logs backend

# Restart backend service
docker-compose restart backend
```

### Reset Everything
```bash
# Stop all services and remove data
docker-compose down -v

# Rebuild and start fresh
docker-compose up -d --build
```

---

Built with ❤️ using Spring Boot, React, and MongoDB