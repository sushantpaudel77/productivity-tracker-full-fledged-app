import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

const API_BASE_URL = process.env.NODE_ENV === 'production' 
  ? 'http://localhost:8080/api' 
  : '/api';

function App() {
  const [habits, setHabits] = useState([]);
  const [newHabit, setNewHabit] = useState({
    name: '',
    description: '',
    targetFrequency: 7
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchHabits();
  }, []);

  const fetchHabits = async () => {
    try {
      setLoading(true);
      const response = await axios.get(`${API_BASE_URL}/habits`);
      console.log('Habits response:', response.data);

      // Adjust this based on your API's actual response structure
      if (Array.isArray(response.data)) {
        setHabits(response.data);
      } else if (response.data.habits && Array.isArray(response.data.habits)) {
        setHabits(response.data.habits);
      } else {
        setHabits([]);
        setError('Unexpected habits data format from API');
      }

      setError('');
    } catch (err) {
      setError('Failed to fetch habits: ' + err.message);
      console.error('Error fetching habits:', err);
    } finally {
      setLoading(false);
    }
  };

  const createHabit = async (e) => {
    e.preventDefault();
    if (!newHabit.name.trim()) {
      setError('Habit name is required');
      return;
    }

    try {
      setLoading(true);
      const response = await axios.post(`${API_BASE_URL}/habits`, newHabit);

      // Append new habit to the list safely
      setHabits(prevHabits => Array.isArray(prevHabits) ? [...prevHabits, response.data] : [response.data]);

      setNewHabit({ name: '', description: '', targetFrequency: 7 });
      setError('');
    } catch (err) {
      setError('Failed to create habit: ' + err.message);
      console.error('Error creating habit:', err);
    } finally {
      setLoading(false);
    }
  };

  const deleteHabit = async (id) => {
    try {
      setLoading(true);
      await axios.delete(`${API_BASE_URL}/habits/${id}`);
      setHabits(prevHabits => (Array.isArray(prevHabits) ? prevHabits.filter(habit => habit.id !== id) : []));
      setError('');
    } catch (err) {
      setError('Failed to delete habit: ' + err.message);
      console.error('Error deleting habit:', err);
    } finally {
      setLoading(false);
    }
  };

  const markHabitComplete = async (habitId, date, completed, notes = '') => {
    try {
      setLoading(true);
      const response = await axios.post(
        `${API_BASE_URL}/habits/${habitId}/entries`,
        null,
        {
          params: { date, completed, notes }
        }
      );

      setHabits(prevHabits => prevHabits.map(habit => 
        habit.id === habitId ? response.data : habit
      ));

      setError('');
    } catch (err) {
      setError('Failed to update habit entry: ' + err.message);
      console.error('Error updating habit entry:', err);
    } finally {
      setLoading(false);
    }
  };

  const getTodayEntry = (habit) => {
    const today = new Date().toISOString().split('T')[0];
    return habit.entries?.find(entry => entry.date === today);
  };

  const getWeeklyProgress = (habit) => {
    const oneWeekAgo = new Date();
    oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);

    const recentEntries = habit.entries?.filter(entry => {
      const entryDate = new Date(entry.date);
      return entryDate >= oneWeekAgo && entry.completed;
    }) || [];

    return recentEntries.length;
  };

  const formatProgressText = (current, target) => {
    if (current >= target) return 'Goal Achieved!';
    return `${current} of ${target}`;
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>Habit Tracker</h1>
        <p>Build consistent habits, track your progress</p>
      </header>

      <main className="main-content">
        {error && <div className="error-message">{error}</div>}

        <section className="habit-form-section">
          <h2>Create New Habit</h2>
          <form onSubmit={createHabit} className="habit-form">
            <input
              type="text"
              placeholder="Enter habit name (e.g., Read for 30 minutes)"
              value={newHabit.name}
              onChange={(e) => setNewHabit({...newHabit, name: e.target.value})}
              required
              maxLength={100}
            />
            <input
              type="text"
              placeholder="Optional description"
              value={newHabit.description}
              onChange={(e) => setNewHabit({...newHabit, description: e.target.value})}
              maxLength={200}
            />
            <input
              type="number"
              min="1"
              max="7"
              placeholder="Weekly target (1-7 times)"
              value={newHabit.targetFrequency}
              onChange={(e) => setNewHabit({...newHabit, targetFrequency: parseInt(e.target.value) || 7})}
            />
            <button type="submit" disabled={loading}>
              {loading ? 'Creating...' : 'Create Habit'}
            </button>
          </form>
        </section>

        <section className="habits-section">
          <h2>Your Habits</h2>
          {loading && habits.length === 0 ? (
            <div className="loading">Loading your habits...</div>
          ) : !Array.isArray(habits) || habits.length === 0 ? (
            <div className="no-habits">
              <p>No habits created yet. Start building your first habit above!</p>
            </div>
          ) : (
            <div className="habits-grid">
              {habits.map(habit => {
                const todayEntry = getTodayEntry(habit);
                const weeklyProgress = getWeeklyProgress(habit);
                const progressPercentage = Math.min(Math.round((weeklyProgress / habit.targetFrequency) * 100), 100);
                const isCompleted = todayEntry?.completed;

                return (
                  <div key={habit.id} className="habit-card">
                    <div className="habit-header">
                      <h3>{habit.name}</h3>
                      <button 
                        onClick={() => deleteHabit(habit.id)}
                        className="delete-btn"
                        disabled={loading}
                        title="Delete habit"
                      >
                        ×
                      </button>
                    </div>
                    
                    {habit.description && (
                      <p className="habit-description">{habit.description}</p>
                    )}
                    
                    <div className="habit-stats">
                      <div className="stat">
                        <span className="stat-label">Weekly Goal</span>
                        <span className="stat-value">{habit.targetFrequency}</span>
                      </div>
                      <div className="stat">
                        <span className="stat-label">Completed</span>
                        <span className="stat-value">{weeklyProgress}</span>
                      </div>
                      <div className="stat">
                        <span className="stat-label">Progress</span>
                        <span className="stat-value">{progressPercentage}%</span>
                      </div>
                    </div>

                    <div className="progress-bar">
                      <div 
                        className="progress-fill" 
                        style={{width: `${progressPercentage}%`}}
                      ></div>
                    </div>

                    <div className="today-action">
                      <span>Today's Status:</span>
                      <button
                        onClick={() => markHabitComplete(
                          habit.id, 
                          new Date().toISOString().split('T')[0], 
                          !isCompleted
                        )}
                        className={`complete-btn ${isCompleted ? 'completed' : ''}`}
                        disabled={loading}
                      >
                        {isCompleted ? 'Completed ✓' : 'Mark Complete'}
                      </button>
                    </div>
                  </div>
                );
              })}
            </div>
          )}
        </section>
      </main>
    </div>
  );
}

export default App;