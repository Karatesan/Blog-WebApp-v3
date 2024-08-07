

export const setItemWithExpiration = (key, value, ttl) =>{
    const now = new Date()

    const item = {
        key: value,
        expiry: now.getTime + ttl
    }

    localStorage.setItem(key, JSON.stringify(item))
}

export const getTokenFromLocalStorage = (key) =>{
    const token = localStorage.getItem(key);
    return token? JSON.parse(token).key:null;
}