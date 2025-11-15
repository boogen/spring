# Unity CSProj Generator

This project automates the generation of `.csproj` files for Unity projects using a Jenkins job.  
It provides two main components:

1. **Jenkinsfile** — defines the Jenkins pipeline that runs the Unity editor command to generate `.csproj` files inside an existing workspace.
2. **run_jenkins_job.sh** — a shell script for triggering the Jenkins job remotely (e.g., from a developer machine or a remote server), polling for completion, and downloading the resulting `.zip` artifact containing all generated project files.

---

## 📁 Project Structure

```
├── Jenkinsfile           # Jenkins pipeline definition for CSProj generation
└── run_jenkins_job.sh    # Remote trigger & artifact download script
```

---

## ⚙️ Jenkins Job Overview

The Jenkins job defined by `Jenkinsfile`:
- Uses the existing Unity workspace (no clean checkout).
- Executes Unity in batch mode to regenerate all `.csproj` and `.sln` files.
- Packages the results into a `.zip` artifact.
- Publishes the artifact for later download via the Jenkins REST API.

Typical job name:  
```
Tools/generate_csproj_files
```

---

## 🚀 Running the Job Remotely

You can trigger and retrieve results using `run_jenkins_job.sh`.

### Prerequisites
- Jenkins account with permissions to trigger jobs and download artifacts.
- A valid Jenkins API token.
- `curl`, `jq`, and `unzip` installed.

### Environment Variables

| Variable | Description |
|-----------|-------------|
| `JENKINS_URL` | Base Jenkins URL (e.g. `https://jenkins.local.tensquaregames.net`) |
| `JENKINS_USER` | Jenkins username |
| `JENKINS_TOKEN` | Jenkins API token |
| `JOB_PATH` | Job path, e.g. `Tools/job/generate_csproj_files` |
| `WORKSPACE` | Optional: specify Jenkins workspace if required |
| `OUTPUT_DIR` | Optional: where to save downloaded `.zip` (default: current dir) |

### Example Usage

```bash
export JENKINS_USER="your.user"
export JENKINS_TOKEN="your_token"
export JENKINS_URL="https://jenkins.local.tensquaregames.net"
export JOB_PATH="Tools/job/generate_csproj_files"

./run_jenkins_job.sh
```

The script will:
1. Trigger the Jenkins job.
2. Poll the Jenkins queue until the job starts.
3. Wait for job completion.
4. Automatically download the `.zip` artifact with `.csproj` files.

Resulting artifact:  
```
csproj.zip
```

---

## 🧩 Example Integration

You can use this script from another automation system (e.g., Kubernetes pod, CI container, or a developer workstation) to ensure you always have an up-to-date set of `.csproj` files generated from Unity, without running Unity locally.

Example quick test from a Kubernetes pod:
```bash
kubectl run -it --rm csproj-check --image=alpine:3.20   --restart=Never   --env JENKINS_USER=your.user   --env JENKINS_TOKEN=your_token   --env JENKINS_URL=https://jenkins.local.tensquaregames.net   --env JOB_PATH=Tools/job/generate_csproj_files   -- sh -c "apk add --no-cache curl jq unzip && ./run_jenkins_job.sh"
```

---

## 🧱 Jenkinsfile Notes

- Pipeline runs inside an existing Unity workspace.
- Avoids unnecessary checkout or clean.
- Publishes artifacts to Jenkins for download.
- Example output artifact: `csproj.zip`.

---

## 🧾 License

Internal use only – proprietary automation for Unity-based projects at Ten Square Games.
