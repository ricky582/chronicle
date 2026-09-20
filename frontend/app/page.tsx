import styles from "./page.module.css";

export default function Home() {
  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <div className={styles.daynav}>
          <button>
            Prev
          </button>
          27/09/2026
          <button>
            Next
          </button>
        </div>
        <div className={styles.log}>
          <textarea name="logentry" />
        </div>
        <button>
          Save
        </button>
      </main>
    </div>
  );
}
