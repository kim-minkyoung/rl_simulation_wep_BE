using UnityEngine;

public class AttackCollider : MonoBehaviour
{
    public int damage = 1; // 피해량

    private void OnTriggerEnter2D(Collider2D other)
    {
        if (other.CompareTag("Enemy")) // 적과 충돌했을 때
        {
            // 적의 체력 감소 로직을 추가합니다.
            EnemyHealth enemyHealth = other.GetComponent<EnemyHealth>();
            if (enemyHealth != null)
            {
                enemyHealth.TakeDamage(damage); // 적에게 피해를 주는 메서드 호출
                Debug.Log("Enemy hit!");
            }
        }
    }
}
