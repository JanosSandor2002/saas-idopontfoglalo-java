# Appointment Booking Platform

> 🇬🇧 **English** | [🇭🇺 Magyar](README.hu.md)

---

## About the Project

An online appointment booking platform designed for beauty-industry service providers such as hairdressers, beauticians, nail salons, and similar businesses.

The goal is to replace phone- and Messenger-based appointment coordination with a simple, self-service booking interface, reducing administrative work and last-minute cancellations (no-shows).

## Two Sides

* **Salon owner/manager** – logs in, manages services, prices, working hours, and views bookings in an overview calendar.
* **Customer** – can book an appointment through the salon's booking page without creating an account.

One account can be associated with multiple salons.

## Subscription Plans

| Plan        | Included                                                                                                 |
| ----------- | -------------------------------------------------------------------------------------------------------- |
| **Basic**   | Calendar module (services, working hours, appointments)                                                  |
| **Pro**     | + Notification module (email confirmations, reminders) + Online Transaction module (deposit/payment)     |
| **Premium** | + SMS notifications, calendar synchronization + Web module (embeddable booking interface / mini website) |

## Main Modules

* **Calendar Module** – management of services, working hours, available and booked time slots.
* **Notification Module** – automated email (later SMS) communication between customers and salons, customer history and notes.
* **Online Transaction Module** – requesting a deposit or full payment during booking through Barion to reduce no-shows.
* **Web Module** – embedding the booking interface into an existing website or providing the salon with a simple dedicated mini website.

## Planned Technology

* **Frontend:** Next.js
* **Backend:** NestJS (modular architecture with modules enabled/disabled according to the subscription plan)
* **Database:** MongoDB
* **Payments:** Barion
* **Email:** AWS SES
* **Calendar synchronization:** One-way `.ics` feed for Google Calendar / Outlook

## Milestones

1. **Concept Development** – problem and target audience definition, feature list (MVP vs. future), user flows, wireframes, domain model, and final technology decisions.
2. **Design → Architecture** – repository structure (monorepo: `apps/frontend`, `apps/backend`, `packages/shared`), API endpoint list, and initial database schema.
3. **Development Environment** – pin Node.js version, Docker Compose (backend, frontend, database), `.env.example`, `.gitignore`.
4. **Backend (NestJS) – with Tests** – project initialization, database connection (Mongoose), feature-based modules, implementation together with unit tests, JWT authentication and tests, DTOs and validation.
5. **Frontend (Next.js) – with Tests** – project initialization, design system (Tailwind), API client layer, main pages (admin dashboard, customer interface), component and integration tests (e.g. React Testing Library).
6. **Payment Integration (Barion) – with Tests** – sandbox account, `payment/start` and `payment/callback` endpoints, split-payment logic in the sandbox, unit and integration tests (successful, failed, and timeout cases), invoicing integration if required.
7. **E2E Testing** – automated testing of the main user flows (registration → booking/purchase → payment), plus manual sandbox testing of the complete process.
8. **CI/CD** – pipeline (lint → build → test) for every pull request, automated Docker image builds, and staging environment.
9. **Production Environment** – server, domain, SSL, secure secret management, activation of the Barion production account, monitoring and logging (e.g. Sentry).
10. **Launch** – onboarding the first real merchant, verifying the first live transaction, establishing a support channel, and continuous monitoring.

## Current Status

The project is currently in **Milestone 1 (Concept Development)**.

The detailed feature list, user flows, domain model, and class architecture are available in the extended concept documentation.

UI and wireframe design have **not yet started**.

## License & Usage Restrictions

**Copyright © 2026. All rights reserved.**

This project and all of its contents, including but not limited to source code, documentation, designs, database structures, assets, and related materials, are proprietary and may not be used without explicit permission from the copyright holder.

Unless prior written permission has been granted, you may **not**:

* copy, reproduce, or redistribute the source code or any substantial part of it;
* use the project or any part of it in another project;
* modify, adapt, or create derivative works based on the project;
* publish or make the source code publicly available;
* use the project or any part of it for commercial purposes;
* sell, sublicense, or otherwise distribute the project or derivative works;
* present the project or any part of it as your own;
* use the project's architecture, implementation, or proprietary materials for a competing product or service.

Viewing, cloning, or accessing this repository does **not** grant any license or other rights to use the project's contents.

Any use beyond personal inspection of the repository requires **explicit prior written permission from the copyright holder**.

For permission requests, please contact the copyright holder.