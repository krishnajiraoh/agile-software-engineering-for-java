# agile-software-engineering-for-java

# Cloud & Micro-services

### On-Premise vs. Public Cloud

- On-premise systems hosted for 1 customer, if there is an issue only 1 customer is affected.T his means there are **X** different on-premise systems, each having its own administrator.
- Public Cloud: Outages apply to **every** customer. **We** have to ensure availability (the work of **X** administrators)

### Public Cloud Requirements

- **Boundless Scalability: millions of users, thousands of servers, petabytes of data, globally distributed**
- **High Availability**: zero downtime deployments, seamless failover
- **Fast Innovation**: develop, build and ship in short cycles within a day

### **Can a monolith fulfil these requirements?**

![Untitled](Cloud%20&%20Micro-services%203735144f2784469b9522f64b00ada2f7/Untitled.png)

### Monolithic Architecture

- 👍
    - Guaranteed transactional consistency for all data
    - Simple deployment, monitoring & troubleshooting
    - Code sharing easy

- 👎
    - Vertical scaling limited by hardware, cost non-linear
    - Systems poorly utilised:
        - Even when the application is not intensively used (non-peak) the full resources are provided
    - HA Setup requires almost double the infrastructure
    - Must be built, tested, deployed and scaled as a whole

### **Idea Of Micro-services**

![Untitled](Cloud%20&%20Micro-services%203735144f2784469b9522f64b00ada2f7/Untitled%201.png)

- rather than having the whole application 'smushed' into one monolith, break the monolith into smaller services
    - each running in it's own process
    - each developed and deployed independently
    

### **Advantages Of Micro-services**

![Untitled](Cloud%20&%20Micro-services%203735144f2784469b9522f64b00ada2f7/Untitled%202.png)

- Unlimited and fine granular **(auto-)scaling possible**
- **Poly skilled teams** can work on services independently
- Deployable individually and frequently
- Each service can be **deployed independently**
- Lower **Risk** of deployment
- **Free choice of technology** for individual services

### **What are the properties of a Micro-service Application?**

- a single application as a **suite** of small services, the "application" is the **sum of its services**
- with each service running in its **own process**
    - Each service has it's own application lifecycle, can be stopped, started, scaled and broken ;-) individually
- communicating with lightweight mechanisms e.g. **HTTP**
- services are built around business capabilities, Each service has a **clearly defined responsibility and interface**
- own one part from DB to UI
    - each service has it's own datastore (no sharing!)
    - the only way for other services to get information from the datastore is via the network endpoints of the owning service
    - Micro-service is transaction boundary: Service can guarantee data consistency only for local data
        - Why no DB sharing? If we share a db between services we break independent deployability, the schema is then a "shared implementation detail" of all services using it. **This leads to tight coupling between the services.**
    - Ideally, even UI part is of service, Why?-> ...otherwise hard to keep independently deployable from UI since UI would be a separate service.

![Untitled](Cloud%20&%20Micro-services%203735144f2784469b9522f64b00ada2f7/Untitled%203.png)

### Difference b/w Restart & Restage:

**When to Restart:**

> Restart your app to refresh the app’s environment after actions such as binding a new service to the app or setting an environment variable that only the app consumes.
> 

**When to Restage:**

> Restage your app if you have changed the environment in a way that affects your staging process, such as setting an environment variable that the buildpack consumes. The staging process has access to environment variables, so the environment can affect the contents of the droplet.
>
